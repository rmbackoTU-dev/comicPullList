# TODO FILE 

**Reminder keep UI elements for JSP and forms simple** 

[X] Move a back up of current comic book features to the complex branch
[X] Update TODO.md and README.md with new development plan
[X] Create a feature branch for pruning, and CRUD feature development
[X] Prune the automation / Non-CRUD features from Java application
  --[X] Remove Unnescessary features
	--[X] Update TestComicIssue to test collection ID, and setters regression test base features.
[] Set up Apache Tomcat environment
[] Set up a MySQL Environment
[] Create a SQL Script to create the schema for ComicIssue Table
[] Create a ComicDatabaseConnector class which establishes JBCD connection to the database
[] Create a ComicCRUDActions class which will contain in-line SQL for Read and Create operations
[] Create a ListComicServlet and test that implements Read action from ComicCRUDActions
[] Create a ComicFormServlet and test that implements Create actions from ComicCRUDActions
[] Delete comics in the database using IN-LINE SQL add to ComicCRUDActions
[] Update comics in the database using IN-LINE SQL add to ComicCRUDActions
[] Create a JSP Form that allows comic creation
[] Create a JSP Page that allows comics to be displayed
[] Add a feature to the JSP display page that allows comics to be deleted
[] Add a way to navigate to the JSP Form with the intent to update a comic tie the intent to a comic update

