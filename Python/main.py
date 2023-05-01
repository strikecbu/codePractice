# create a calculator that can convert user input days to hours
# 1. Create the variables for the number of days and hours per day
numbers_of_unit = 24
name_of_unit = "hours"


# 2. Calculate the number of hours by multiplying the number of days by 24
def convert_days_to_unit(number_of_days):
    return f"{number_of_days} days are {number_of_days * numbers_of_unit} {name_of_unit}"


def validate_and_execute():
    try:
        user_input_number = int(user_input)
        if user_input_number > 0:
            calculated_value = convert_days_to_unit(user_input_number)
            print(calculated_value)
        elif user_input_number == 0:
            print("You entered a 0, please enter a valid positive number")
        else:
            print("You entered a negative number, no conversion for you")
    except ValueError:
        print("Your input is not a number. Don't ruin my program")


# 3. Print the result
user_input = ""
while user_input != "exit":
    user_input = input("Enter the number of days you want to convert to hours: \n")
    validate_and_execute()
