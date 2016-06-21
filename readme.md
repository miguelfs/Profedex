# PROFEDEX

Profedex is an Android application to display data about teachers of Universidade Federal do Rio de Janeiro.

## Usage
The app helps students to get information about their professors, helping them in their academic life. It contains information about professors from Escola Politécnica. Students can include positive and negative characteristics about the professors. They can also include additional information and data about them.

##Main Features (MVP)
* A ranking to users evaluate good and bad information about teachers. The bad ones will be deleted from the system. The evaluating system will use an algorithm to give preference to recent votes.
* A report button to alert any type of offensive, abusive or fake information.

##Extra Features
* A facial recognition tool to help identify the professor using their phone camera.
* A searching system to find teachers according to a filter.

##User login
The user needs to register and login to submit new data about the professors to the database.

##Data
A database will be created to store the data from the class Professor which will include:

* Professor name
* Professor description
* Professor's room and classrooms he/she teaches in
* Subjects he/she teaches
* Where they live 
* What they eat
* Audio
* Difficulty level
* Didacticism level

##Activies
The app will use two main Activities:
* Activity with a list of professors (Recycler View);
* Activity with professor's data and description (Tabbed Activity);

## Dependencies
* Volley 
```
git clone https://android.googlesource.com/platform/frameworks/volley
```
Import the library module to your project:

1. Click File > New > Import Module.
2. Enter the location of the library module directory then click Finish.

##Terms and conditions
The developers do not take responsability about the content generated by the users.
