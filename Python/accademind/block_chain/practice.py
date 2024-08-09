chain_box = []


def get_user_input():
    user_input = input("Enter a transaction: ")
    return user_input


def add_block(val):
    chain_box.append(val)
    print(f"now chain_box: {chain_box}")


def main():
    while True:
        user_input = get_user_input()
        if user_input == "q":
            print(f"Goodbye, final chain_box: {chain_box}")
            return
        add_block(float(user_input))


main()
