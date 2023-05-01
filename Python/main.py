# create a calculator that can convert user input days to hours
# 1. Create the variables for the number of days and hours per day
numbers_of_unit = 24
name_of_unit = "hours"


# 2. Calculate the number of hours by multiplying the number of days by 24
def convert_days_to_unit(number_of_days):
    return f"{number_of_days} days are {number_of_days * numbers_of_unit} {name_of_unit}"


# 3. Print the result
numbers_of_days = input("Enter the number of days you want to convert to hours: \n")
convert_result = convert_days_to_unit(int(numbers_of_days))
print(convert_result)
