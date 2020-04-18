import math
import random

# QUESTION 1
def q1(bw_list, should_swap = False):
    # default should_swap is false because
    # first element always needs to be black

    if should_swap:
        # interchanging boxes
        bw_list[0], bw_list[len(bw_list) - 1] = bw_list[len(bw_list) - 1], bw_list[0]

    if len(bw_list) > 2:
        inner_list = q1(bw_list[1: len(bw_list) - 1], not should_swap)
        # merge lists because python does not allow same memory to be sub setted
        # instead it copies the array so I need to merge it with the original again
        for i in range(0, len(bw_list) - 2):
            bw_list[i + 1] = inner_list[i]

    return bw_list

def q1_test():
    print("QUESTION 1:")

    test_count = 10
    for i in range(0, test_count):
        print(f"Test Case #{i + 1}")
        n = random.randint(1, 10)
        initial_list = []
        alternating_list = []
        for i in range(0, 2 * n):
            if i % 2 == 0:
                alternating_list.append('B')
            else:
                alternating_list.append('W')

            if i < n:
                initial_list.append('B')
            else:
                initial_list.append('W')

        print(f"Initial List:\t\t {initial_list}")
        print(f"Expected List:\t\t {alternating_list}")

        final_list = q1(initial_list)
        print(f"Calculated List:\t {final_list}")
        print()

# QUESTION 2
def q2(coin_list):
    result = q2_rec(coin_list, 0, len(coin_list) - 1)
    return result

def q2_rec(coin_list, start_index, end_index):
    size = len(coin_list)
    right_offset = 0

    # if there are odd number of coins in the pile
    if size % 2 == 1:
        right_offset = 1

    half_size = math.floor(size / 2)
    left_pile = coin_list[0 : half_size]
    right_pile = coin_list[half_size : size - right_offset]

    diff = end_index - start_index + 1

    if sum(left_pile) == sum(right_pile):
        if size == 1:
            return start_index
        elif size % 2 == 1:
            return end_index
    elif sum(left_pile) < sum(right_pile):
        # Fake coin is in the left pile
        return q2_rec(left_pile, start_index, start_index + diff // 2 - 1)
    elif sum(right_pile) < sum(left_pile):
        # Fake coin is in the right pile
        return q2_rec(right_pile, start_index + diff // 2, end_index - right_offset)

def q2_test():
    print("QUESTION 2:")

    test_count = 10
    for i in range(0, test_count):
        print(f"Test Case #{i + 1}")
        random_list = []
        list_len = random.randint(1, 50)
        for i in range(0, list_len):
            random_list.append(2)

        generated_fake_index = random.randint(0, list_len - 1)
        random_list[generated_fake_index] = 1

        print(f"Coins: {random_list}")
        fake_coin_index = q2(random_list)
        print(f"Expected Fake Coin Index: {generated_fake_index}")
        print(f"Calculated Fake Coin Index: {fake_coin_index}")
        print()

# PARTITION FUNCTION THAT WILL BE USED IN QUESTIONS 3 AND 4
def lomuto_partition(ar, low, high):
    swaps = 0
    pivot = ar[low]
    s = low

    for i in range(low + 1, high + 1):
        if(ar[i] < pivot):
            s = s + 1
            ar[s], ar[i] = ar[i], ar[s]
            swaps = swaps + 1

    ar[low], ar[s] = ar[s], ar[low]
    swaps = swaps + 1
    return s, swaps

# QUESTION 3
def q3_quick_sort(ar, low, high):
    if(low < high):
        partition_point, swap_count = lomuto_partition(ar, low, high)
        swap_count += q3_quick_sort(ar, low, partition_point)
        swap_count += q3_quick_sort(ar, partition_point + 1, high)
        return swap_count
    return 0

def q3_insertion_sort(ar):
    swap_count = 0

    for index in range(1, len(ar)):
        val = ar[index]
        ind = index

        while ind > 0 and ar[ind - 1] > val:
            ar[ind] = ar[ind -1]
            ind = ind - 1
            swap_count = swap_count + 1

        ar[ind] = val

    return swap_count

def q3_test():
    print("QUESTION 3:")

    test_count = 10
    for i in range (0, test_count):
        print(f"Test Case #{i + 1}")
        list_len = random.randint(1, 50)
        random_integers = [random.randint(0, 100) for iter in range(list_len)]
        qs_list = random_integers[0: list_len]
        is_list = random_integers[0: list_len]
        print(f"Unsorted List:\t\t\t\t\t {random_integers}")
        qsort_swap_count = q3_quick_sort(qs_list, 0, len(qs_list) - 1)
        print(f"List Sorted by Quicksort:\t\t {qs_list}, Swap Count: {qsort_swap_count}")

        isort_swap_count = q3_insertion_sort(is_list)
        print(f"List Sorted by Insertion Sort:\t {is_list}, Swap Count: {isort_swap_count}")
        print()

# QUESTION 4
def q4(ar):
    return q4_rec(ar, 0, len(ar) - 1)

def q4_rec(ar, low, high):
    updated_pivot_index, swaps = lomuto_partition(ar, low, high)
    if updated_pivot_index == len(ar) // 2:
        return ar[updated_pivot_index]
    elif updated_pivot_index < len(ar) // 2:
        return q4_rec(ar, updated_pivot_index + 1, high)
    else:
        return q4_rec(ar, low, updated_pivot_index - 1)


def q4_test():
    print("QUESTION 4:")

    test_count = 10
    for i in range(0, test_count):
        print(f"Test Case #{i + 1}")
        list_len = random.randint(1, 50)
        if list_len % 2 == 0:
            list_len += 1
        random_integers = [random.randint(0, 100) for iter in range(list_len)]

        sorted_list = random_integers[0 : list_len]
        q3_quick_sort(sorted_list, 0, list_len - 1)

        print(f"List: {random_integers}")
        print(f"Expected Median: {sorted_list[list_len // 2]}")
        median = q4(random_integers)
        print(f"Calculated Median: {median}")
        print()

# QUESTION 5
def mul(ar):
    result = 1
    for n in ar:
        result *= n
    return result

def q5(ar):
    min_sum = (max(ar) + min(ar)) * len(ar) // 4

    # Getting all subsets by brute forcing all 2^n possible cases
    subsets = []
    for i in range(2 ** len(ar)):
        subset = []
        for k in range(len(ar)):
            if i & 1 << k:
                subset.append(ar[k])
        subsets.append(subset)

    current_min_mult = math.inf
    current_answer = []
    for subset in subsets:
        if sum(subset) >= min_sum and mul(subset) < current_min_mult:
            current_min_mult = mul(subset)
            current_answer = subset

    return current_answer


def q5_test():
    print("QUESTION 5:")

    test_count = 10
    for i in range(0, test_count):
        print(f"Test Case #{i + 1}")

        list_len = random.randint(5, 15)
        random_integers = [random.randint(0, 100) for iter in range(list_len)]

        print(f"List: {random_integers}")

        optimal_sub_array = q5(random_integers)
        print(f"Optimal Sub-Array: {optimal_sub_array}")
        print()

q1_test()
q2_test()
q3_test()
q4_test()
q5_test()
