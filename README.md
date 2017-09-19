#Blinker Android Candidate Interview Project 
1.    Using the JSON file (vehicles.json) included in the project, create a scrolling List that shows each vehicle's `year`, `make`, and `model` (1999 Honda Accord) in each item, along with its mileage.
* The year, make, and model should go in the upper left corner of the cell with 16dp margins on the left and the top.
* The vehicle's mileage should go in the bottom right corner of the cell with 16dp margins on the right and the bottom.
* The vehicles.json file has many duplicate vehicles for the sake of getting a large, populated list. Don't worry about filtering out duplicates.
2. Include a search field at the top of the screen to filter vehicles in the list.
* Should be able to search any combination of year, make, or model. e.g. "1999", "honda accord", "1999 accord", "honda 1999", etc.
3. When an item is tapped, load the image from the URL (`image_url`) included in the JSON file and display it on a new screen with a 4:3 aspect ratio
5. Write all code in Java or Kotlin
6. If you get stuck anywhere along the way, feel free to ask for help.
7. You may use whatever resources you'd like to complete the project including 3rd party libraries, search Stack Overflow, ask for help, whatever. The only rule is that whatever implementation you come up with must be your own.

#Bonus (Not required and will not be counted against you should you choose not to complete this)
1. Split the list into different sections with headers for each year.
Example would be a list header item "2016" followed by all vehicles with the year "2016" and then the header "2015" followed by vehicles for that year.
