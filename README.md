# Agoda
## Created ChangePassword Function Requirement
  - Password requirement
    - At least 18 alphanumeric characters and list of special chars !@#$&*
    - At least 1 Upper case, 1 lower case ,least 1 numeric, 1 special character
    - No duplicate repeat characters more than 4
    - No more than 4 special characters
    - 50 % of password should not be a number
  - Change password requirement
    - Old password should match with system
    - New password should be a valid password
    - password is not similar to old password < 80% match.

## Test Cases
  - TC1: match password requirement
  - TC2: over 18 alphanumeric characters and list of special characters
  - TC3: no uppercase
  - TC4: no lowercase
  - TC5: no numeric
  - TC6: no special character
  - TC7: duplicate more than 4 characters
  - TC8: more than 4 special characters
  - TC9: more than 50% is number
