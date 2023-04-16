| Nr  |                             API Tests                              | Status |
|:----|:------------------------------------------------------------------:|:------:|
| 1   |          Positive test - correct order ID and status code          |   OK   |
| 2   |         Negative test - incorrect order ID and status code         |   OK   |
| 3   | Negative test with parameters - incorrect order ID and status code |   OK   |
| 4   |  Positive test with parameters - correct order ID and status code  |   OK   |
| 5   |            Negative test - post request without header             |   OK   |
| 6   |     Positive test check status code was "OPEN" with correct ID     |   OK   |

| Nr  |                PetStore API Tests                 | Status |
|:----|:-------------------------------------------------:|:------:|
| 1   | Correct IDs - check order placed message returned |   OK   |
| 2   |          Correct IDs - check status code          |   OK   |
| 3   |         Incorrect IDs - check status code         |   OK   |

| Nr  |         UI Tests Login - Tallinn Delivery         | Status |
|:----|:-------------------------------------------------:|:------:|
| 1   |           Log in with valid credentials           |   OK   |
| 2   | Log in with correct login and incorrect password  |   OK   |
| 3   | Password input incorrect - less than 8 characters |   OK   |
| 4   |         Log in with password field empty          |   OK   |
| 5   |        Log in with all fields field empty         |   OK   |

| Nr  |     UI element - Tallinn Delivery login page      |                                       x path                                        |
|:----|:-------------------------------------------------:|:-----------------------------------------------------------------------------------:|
| 1   |                Log in input field                 |                        //input[@data-name="username-input"]                         |
| 2   |               Password input field                |                        //input[@data-name="password-input"]                         |
| 3   | Password input incorrect - less than 8 characters |                        //button[@data-name="signIn-button"]                         |
| 4   |      Error message when password field empty      | //span[@class='form-error form-error_active' and @data-name='username-input-error'] |
| 5   |           Incorrect credentials pop-up            |                    //div[@data-name='authorizationError-popup']                     |