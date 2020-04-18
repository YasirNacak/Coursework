import math


# QUESTION 1 - SPECIAL ARRAYS
def q1_get_min_index(array, lo, hi, current_min=math.inf):
    result = lo

    for i in range(lo, hi):
        if array[i] < current_min:
            current_min = array[i]
            result = i

    return result


def q1_combine(array, evens):
    res = []
    ln = len(array[0])
    for i in range(len(array)):
        left = evens[i]
        right = ln - 1 if i == len(array) - 1 and len(evens) == len(array) else evens[i + 1]
        pos = q1_get_min_index(array[i], left, right + 1, array[i][left])
        res.append(evens[i])
        res.append(pos)

    if len(evens) > len(array):
        res.append(evens[-1])

    return res


def q1_leftmost_min_aux(array):
    if len(array) == 1:
        pos = q1_get_min_index(array[0], 0, len(array[0]))
        return [pos]

    even_result = q1_leftmost_min_aux(array[0:len(array):2])
    res = q1_combine(array[1:len(array):2], even_result)
    return res


def q1_leftmost_min(array):
    print(f"Finding leftmost minimums for:\n{array}")

    result = []
    indices = q1_leftmost_min_aux(array)

    for i in range(0, len(indices)):
        result.append(array[i][indices[i]])

    return result


def q1_fix_non_special(array):
    print(f"Array to fix:\n{array}")

    min_indices = []

    for i in range(0, len(array)):
        min_indices.append(q1_get_min_index(array[i], 0, len(array[i])))

    faulty_rows_aux = []

    for i in range(0, len(min_indices)):
        if 0 < i < len(min_indices) - 1 and min_indices[i - 1] > min_indices[i]:
            faulty_rows_aux.append((i, min_indices[i]))
        elif 0 < i < len(min_indices) - 1 and min_indices[i + 1] < min_indices[i]:
            faulty_rows_aux.append((i, min_indices[i]))

    faulty_rows = []
    for index in faulty_rows_aux:
        if index not in faulty_rows:
            faulty_rows.append(index)

    if len(faulty_rows) > 1:
        print("Can not be solved")
        return

    f_row = faulty_rows[0][0]
    f_element = faulty_rows[0][1]
    f_min = array[f_row][f_element]
    make_min_to = min_indices[f_row - 1]

    print(f"Changing {f_row}th row's {make_min_to}th element lesser than {f_min} will solve the array.")

    return


def q1_test():
    print_question_number(1)

    special_array = [
        [10, 17, 13, 28, 23],
        [17, 22, 16, 29, 23],
        [24, 28, 22, 34, 24],
        [11, 13, 6, 17, 7],
        [45, 44, 32, 37, 23],
        [36, 33, 19, 21, 6],
        [75, 66, 51, 53, 34],
    ]

    not_special_array = [
        [37, 23, 22, 32],
        [21, 6, 7, 10],
        [53, 34, 30, 29],
        [32, 13, 9, 6],
        [43, 21, 15, 8],
    ]

    q1_fix_non_special(not_special_array)

    print(f"Leftmost Minimums Are: {q1_leftmost_min(special_array)}")


# QUESTION 2 - KTH ELEMENT OF TWO MERGED SORTED ARRAYS
def q2(first_list, second_list, k):
    if len(first_list) == 0:
        return second_list[k]
    elif len(second_list) == 0:
        return first_list[k]

    mid_first_list = len(first_list) // 2
    mid_second_list = len(second_list) // 2

    if mid_first_list + mid_second_list < k:
        if first_list[mid_first_list] > second_list[mid_second_list]:
            return q2(first_list, second_list[mid_second_list + 1:], k - mid_second_list - 1)
        else:
            return q2(first_list[mid_first_list + 1:], second_list, k - mid_first_list - 1)
    else:
        if first_list[mid_first_list] > second_list[mid_second_list]:
            return q2(first_list[:mid_first_list], second_list, k)
        else:
            return q2(first_list, second_list[:mid_second_list], k)


def q2_test():
    print_question_number(2)

    first_list = [4, 6, 15, 21, 40]
    second_list = [2, 5, 16, 17, 24]
    k = 5
    kth_element = q2(first_list, second_list, k)

    print(f"First List: {first_list}")
    print(f"Second List: {second_list}")
    print(f"{k + 1}th Element of the merged list {kth_element}")


# QUESTION 3 - CONTIGUOUS SUBSET WITH LARGEST SUM
def q3_cross_sum(ar, low, mid, high):
    left_sum = -math.inf
    temp_sum = 0

    for i in range(mid, low-1, -1):
        temp_sum = temp_sum + ar[i]
        if temp_sum > left_sum:
            left_sum = temp_sum

    right_sum = -math.inf
    temp_sum = 0

    for i in range(mid+1, high+1):
        temp_sum = temp_sum + ar[i]
        if temp_sum > right_sum:
            right_sum = temp_sum

    return left_sum + right_sum


def q3(ar, lo, hi):
    if hi == lo:
        return ar[hi]

    mid = (hi + lo) // 2

    maximum_sum_left_subset = q3(ar, lo, mid)
    maximum_sum_right_subset = q3(ar, mid + 1, hi)
    maximum_sum_crossing_subset = q3_cross_sum(ar, lo, mid, hi)

    return max(maximum_sum_left_subset, maximum_sum_right_subset, maximum_sum_crossing_subset)


def q3_test():
    print_question_number(3)

    ar = [5, -6, 6, 7, -6, 7, -4, 3]
    max_sum = q3(ar, 0, len(ar) - 1)

    print(f"List: {ar}")
    print(f"Maximum sum that can be found from a contiguous subset: {max_sum}")


# QUESTION 4 - FIND IF GRAPH IS BIPARTITE
def q4_get_adjacent_vertices(graph, vertex):
    result = []

    for i in range(0, len(graph[vertex])):
        if graph[vertex][i] == 1:
            result.append(i)

    return result


def q4(graph, source):
    queue = []
    colors = []

    for v in range(0, len(graph)):
        colors.append(0)

    colors[source] = 1
    queue.append(source)

    while queue:
        u = queue.pop(0)

        for v in q4_get_adjacent_vertices(graph, u):
            if colors[v] == 0:
                colors[v] = 1 if colors[u] == -1 else -1
                queue.append(v)
            elif colors[v] == colors[u]:
                return False

    return True


def q4_test():
    print_question_number(4)

    # Graph, represented by an adjacency matrix
    '''
    G = [
        [0, 1, 0, 0, 0, 0, 0, 0],
        [0, 0, 1, 0, 0, 1, 0, 0],
        [0, 1, 0, 1, 0, 0, 1, 0],
        [0, 0, 1, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 1, 0, 0],
        [0, 1, 0, 0, 1, 0, 1, 0],
        [0, 0, 1, 0, 0, 1, 0, 1],
        [0, 0, 0, 0, 0, 0, 1, 0],
    ]
    '''
    '''
    G = [
        [0, 1, 0, 0, 0, 0],
        [0, 0, 1, 0, 0, 0],
        [0, 0, 0, 1, 0, 0],
        [0, 0, 0, 0, 1, 0],
        [0, 0, 0, 0, 0, 1],
        [1, 0, 0, 0, 0, 0],
    ]
    '''
    '''
    G = [
        [0, 1, 0, 0, 0],
        [0, 0, 1, 0, 0],
        [0, 0, 0, 1, 0],
        [0, 0, 0, 0, 1],
        [1, 0, 0, 0, 0],
    ]
    '''
    G = [
        [0, 1, 1, 1],
        [1, 0, 1, 0],
        [1, 1, 0, 1],
        [1, 0, 1, 0]
    ]

    is_bipartite = q4(G, 0)

    print(f"Adjacency Matrix: {G}")
    print(f"Is Graph Bipartite: {is_bipartite}")


# QUESTION 5 - BEST DAY TO BUY GOODS
def q5_find_max_div_and_conquer(ar, lo , hi):
    if hi - lo == 1:
        return ar[lo]

    mid = (lo + hi) // 2
    u = q5_find_max_div_and_conquer(ar, lo, mid)
    v = q5_find_max_div_and_conquer(ar, mid, hi)

    return max(u, v)


def q5(costs, prices):
    profits = []

    for i in range(0, len(costs) - 1):
        profits.append(prices[i + 1] - costs[i])

    max_profit = q5_find_max_div_and_conquer(profits, 0, len(profits))

    return profits.index(max_profit) if max_profit > 0 else -1


def q5_test():
    print_question_number(5)

    costs = [5, 11, 2, 21, 5, 7, 8, 12, 13, math.inf]
    prices = [math.inf, 7, 9, 5, 21, 7, 13, 10, 14, 20]

    max_profit_day = q5(costs, prices)

    print(f"Costs: {costs}")
    print(f"Prices: {prices}")

    if max_profit_day == -1:
        print(f"Can not earn profit from any day.")
    else:
        print(f"Day to buy goods for the maximum profit is: {max_profit_day + 1}")


# UTILITY
def print_question_number(q_number):
    print(f"\nTESTING QUESTION #{q_number}")


def test_all():
    q1_test()
    q2_test()
    q3_test()
    q4_test()
    q5_test()


if __name__ == '__main__':
    test_all()
