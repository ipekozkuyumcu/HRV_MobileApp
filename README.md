# HRVMobileApp
This mobile application is built as a graduation project for Bahcesehir University Software Engineering Department. 

Hearth Rate Variability Mobile Application is designed to obtain data from the portable device, which is built by Biomedical Engineering team, and interpret HRV changes related to time as a graphic.

Users can create their personal profile for the application. UserID is created and saved automatically by Firebase Databse. 
The HRV data obtained from the device has to be imported into the database under related UserID manually due to the lack of bluetooth connection. 
When the user enters their account using the application, they can display their HRV change realted to time graph on the home page. 

If there is a significant decrease in user’s HRV, application will provide a notification to the user for them to check their health condition, or for them to be careful about diseases, because low HRV means poor prognosis.
If there is an increase in HRV, application provides a notification to the user by asking them what they have been up to, because increase in HRV means they have been under some kind of stimuli. 
By the feedback of the users, the application can also collect data for research.
