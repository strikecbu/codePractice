chain_box = [0]


def get_user_input():
    user_input = input("Enter a number: ")
    return user_input


def add_block(val):
    chain_box.append()
    print(chain_box)


def main():
    while True:
        user_input = get_user_input()
        if user_input == "q":
            return
        add_block(user_input)


main()
