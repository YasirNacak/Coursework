# QUESTION 1 - RUNNING TWO BUSINESSES AT DIFFERENT CITIES
def q1(ny_costs, sf_costs, moving_cost):
    months = len(ny_costs)
    ny_dp = [0] * (months + 1)
    sf_dp = [0] * (months + 1)

    for i in range(1, months + 1):
        ny_dp[i] = ny_costs[i - 1] + min(ny_dp[i - 1], sf_dp[i - 1] + moving_cost)
        sf_dp[i] = sf_costs[i - 1] + min(sf_dp[i - 1], ny_dp[i - 1] + moving_cost)

    return min(ny_dp[months], sf_dp[months])


def q1_test():
    print_question_number(1)

    ny_costs = [1, 3, 20, 30]
    sf_costs = [50, 20, 2, 4]
    moving_cost = 10

    print(f"NY running costs: {ny_costs}")
    print(f"SF running costs: {sf_costs}")
    print(f"Cost of moving between two cities: {moving_cost}")

    min_cost = q1(ny_costs, sf_costs, moving_cost)

    print(f"Minimum total operation cost is: {min_cost}")


# QUESTION 2 - SESSION SCHEDULING
def q2(sessions):
    sorted_sessions = sorted(sessions, key=lambda tup: tup[1])

    ans = [sorted_sessions[0]]
    latest_end_time = sorted_sessions[0][1]

    for session in sorted_sessions:
        if session[0] >= latest_end_time:
            latest_end_time = session[1]
            ans.append(session)

    return ans


def q2_test():
    print_question_number(2)

    sessions = [(0, 6), (1, 2), (3, 4), (5, 7), (5, 9), (8, 9)]
    # sessions = [(10, 20), (12, 25), (20, 30)]
    print(f"All Sessions Are: {sessions}")

    maximum_sessions = q2(sessions)

    print(f"Count: {len(maximum_sessions)}, Sessions: {maximum_sessions}")


# QUESTION 3 - SUBSET WITH SUM = 0
def q3_recursive_memoization(array, i, total, memo):
    if i >= len(array):
        return 1 if total == 0 else 0

    if (i, total) not in memo:
        count = q3_recursive_memoization(array, i + 1, total, memo)
        count += q3_recursive_memoization(array, i + 1, total - array[i], memo)
        memo[(i, total)] = count

    return memo[(i, total)]


def q3_f_subset(array, total, memo):
    subset = []
    for i, x in enumerate(array):
        if q3_recursive_memoization(array, i + 1, total - x, memo) > 0:
            subset.append(x)
            total -= x
    return subset


def q3(array):
    memo = dict()
    return q3_f_subset(array, 0, memo)


def q3_test():
    print_question_number(3)

    array = [-1, 6, 4, 2, 3, -7, -5]

    print(f"Numbers: {array}")

    answer = q3(array)

    print(f"Subset that sums up to 0 is: {answer}")


# QUESTION 4 - SEQUENCE ALIGNMENT
def q4(sequence_a, sequence_b, match_score, mismatch_score, gap_score):
    width = len(sequence_a) + 1
    height = len(sequence_b) + 1
    dp = [[0 for _ in range(0, width)] for _ in range(0, height)]

    for i in range(0, len(dp[0])):
        dp[0][i] = i * gap_score

    for i in range(0, len(dp)):
        dp[i][0] = i * gap_score

    for i in range(1, len(dp)):
        for j in range(1, len(dp[i])):
            m_or_mm_score = dp[i - 1][j - 1] + match_score if sequence_b[i - 1] == sequence_a[j - 1] else dp[i - 1][j - 1] + mismatch_score
            dp[i][j] = max(dp[i - 1][j] + gap_score, dp[i][j - 1] + gap_score, m_or_mm_score)

    return dp[height - 1][width - 1]


def q4_test():
    print_question_number(4)

    sequence_a = "ALIGNMENT"
    sequence_b = "SLIME"

    match_score = 2
    mismatch_score = -2
    gap_score = -1

    print(f"Sequence A: {sequence_a}, Sequence B: {sequence_b}")
    print(f"Match Score: {match_score}, Mismatch Score: {mismatch_score}, Gap Score: {gap_score}")

    max_score = q4(sequence_a, sequence_b, match_score, mismatch_score, gap_score)

    print(f"Maximum Score: {max_score}")


# QUESTION 5 - MINIMUM OPERATION COST
def q5(array):
    sorted_array = sorted(array)
    result = 0

    while len(sorted_array) > 2:
        current_array = [sorted_array[0] + sorted_array[1]]
        result += current_array[0]
        for i in range(2, len(sorted_array)):
            current_array.append(sorted_array[i])
        sorted_array = sorted(current_array)

    return result + sorted_array[0] + sorted_array[1]


def q5_test():
    print_question_number(5)

    array = [14, 2, 7, 5, 11]
    print(f"Array: {array}")

    answer = q5(array)
    print(f"Minimum operations: {answer}")


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
