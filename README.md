# FullScreen
An application that allows you to display current sales information on the second screen of the cashier.
An exchange is carried out through exchange json file. 

Exchange description:
The main system generates an exchange JSON file for any changes to the sales document in a specific folder. 


File example:
{
    "rows": [
        {
            "item": "Test item",
            "quantity": 1,
            "price": 6.99,
            "sum": 6.99,
            "num": 1
        },
        {
            "item": "Test item 1",
            "quantity": 1,
            "price": 7,
            "sum": 7,
            "num": 2
        },
        {
            "item": "Test item 2",
            "quantity": 1,
            "price": 11.99,
            "sum": 11.99,
            "num": 3
        }
    ],
    "result": 25.98,
    "discount": 0,
    "total": 25.98,
    "cardNumber": " "
}
When there is no sale, a video is displayed, the path to the video file is specified in the program settings as well as the path to the exchange file.  
Current application was developed for Ali Nino bookstores(it contains AliNino logo), in case of use in other projects you can change logos to your desired ones.
