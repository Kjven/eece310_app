eece310_app
===========

Android app for EECE 310 project.
Displays Tidal data in a user-friendly format.


Notes:

Put stations and sid statically into app. 
Move HTML parsing and requests to another class. 
Modularize by moving async task to another file
	Need to make it AsyncTask<string, void, GraphViewSeries>
	Graph view series should have name of tide station, and data in graphviewdata objects which are a pair of doubles. 
	Each series should have the x-values as UTC / some sort of date/time.
Move functionality to proper methods, not in big functions.

UnitTesting, especially for HTML parsing. Use some example HTML statically embedded as a test.

Clean up the UI. 


GPS.
