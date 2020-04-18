import cv2 as cv
import numpy as np

def PathOpening(source, pathLength):
    height, width, depth = source.shape

    temp = np.zeros((height, width), np.int8)

    SN = Compute(source, temp, [-1, 0, +1], [+1, +1, +1], [-1, 0, +1], [-1, -1, -1])
    WE = Compute(source, temp, [-1, -1, -1], [-1, 0, +1], [+1, +1, +1], [-1, 0, +1])
    SWNE = Compute(source, temp, [0, +1, +1], [-1, -1, 0], [-1, -1, 0], [0, +1, +1])
    NWSE = Compute(source, temp, [0, -1, -1], [-1, -1, 0], [+1, +1, 0], [0, +1, +1])

    cv.imshow("SN", SN)
    cv.imshow("WE", WE)
    cv.imshow("SWNE", SWNE)
    cv.imshow("NWSE", NWSE)

    final = MixMax(SN, WE)
    final = MixMax(final, SWNE)
    final = MixMax(final, NWSE)

    cv.imshow("final", final)
    return 0

def MixMax(x, y):
    height, width = x.shape

    result = np.zeros((height, width), np.uint8)

    for j in range(0, height):
        for i in range(0, width):
            result[i, j] = max(x[i, j], y[i, j])

    return result

def Compute(source, temp, xOffsetsUp, yOffsetsUp, xOffsetsDown, yOffsetsDown):
    height, width, depth = source.shape
    result = np.zeros((height, width), np.uint8)
    temp.fill(-1)

    for y in range(0, height):
        for x in range(0, width):
            if source[x, y].all() == 1:
                u = ComputeInDirection(source, temp, x, y, xOffsetsUp, yOffsetsUp)
                d = ComputeInDirection(source, temp, x, y, xOffsetsDown, yOffsetsDown)
                result[x, y] = u + d + 1

    return result

def ComputeInDirection(source, temp, x, y, xOffsets, yOffsets):
    height, width, depth = source.shape

    if source[x, y][0].all() == 0:
        temp[x, y] = 0
        return 0

    ls = [0, 0, 0]

    for i in range(0, 3):
        if x + xOffsets[i] >= 0 and x + xOffsets[i] <= width - 1 and y + yOffsets[i] >= 0 and y + yOffsets[i] <= height - 1:
            ls[i] = temp[x + xOffsets[i], y + yOffsets[i]]

    for i in range(0, 3):
        if ls[i] == -1:
            ls [i] = ComputeInDirection(source, temp, x + xOffsets[i], y + yOffsets[i], xOffsets, yOffsets)

    lm = max(ls) + 1
    temp[x, y] = lm
    return lm

if __name__ == '__main__':
    img = cv.imread("test.jpg")

    cv.imshow("original", img)

    PathOpening(img, 5)

    cv.waitKey(0)
    cv.destroyAllWindows()
