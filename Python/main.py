# create a calculator that can convert user input days to hours
# 2. Calculate the number of hours by multiplying the number of days by user input unit
def convert_days_to_unit(number_of_days, name_of_unit):
    if name_of_unit == "hours":
        return f"{number_of_days} days are {number_of_days * 24} hours"
    elif name_of_unit == "minutes":
        return f"{number_of_days} days are {number_of_days * 24 * 60} minutes"
    else:
        return "unsupported unit"


def validate_and_execute(user_input_split):
    try:
        user_input_dictionary = {"days": user_input_split[0], "unit": user_input_split[1]}
        user_input_number = int(user_input_dictionary["days"])
        if user_input_number > 0:
            calculated_value = convert_days_to_unit(user_input_number, user_input_dictionary["unit"])
            print(calculated_value)
        elif user_input_number == 0:
            print("You entered a 0, please enter a valid positive number")
        else:
            print("You entered a negative number, no conversion for you")
    except ValueError:
        print("Your input is not a number. Don't ruin my program")
    except IndexError:
        print("Your input is not match format. Don't ruin my program")


# 3. Print the result
user_input = ""
while user_input != "exit":
    user_input = input("Enter the number of days and unit you want to convert, split with colon\n")
    user_split_data = user_input.split(":")
    validate_and_execute(user_split_data)
