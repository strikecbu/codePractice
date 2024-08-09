chain_box = [[0]]
last_transaction_block = [0]


def get_user_input():
    user_input = input("Enter a transaction: ")
    return user_input


def get_last_transaction():
    return chain_box[-1]


def add_block(val, last_transaction):
    global last_transaction_block
    last_transaction_block = last_transaction
    print(f"last_transaction_block: {last_transaction_block}")
    chain_box.append([last_transaction_block, val])
    print(f"now chain_box: {chain_box}")


def main():
    while True:
        user_input = get_user_input()
        if user_input == "q":
            print(f"Goodbye, final chain_box: {chain_box}")
            return
        add_block(float(user_input), get_last_transaction())


main()
