from helper import validate_and_execute, user_input_message
# create a calculator that can convert user input days to hours

# 3. Print the result
user_input = ""
while user_input != "exit":
    user_input = input(user_input_message)
    user_split_data = user_input.split(":")
    validate_and_execute(user_split_data)
