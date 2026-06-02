<html>
    <head>
        <title> Comic Book Pull List--Entry</title>
    </head>
    <body>
        <p><b>Please enter a your comics information below:</b></p>
        <form method="POST" action="${pageContext.request.contextPath}/createComic">
            <label>Comic Name:</label>
            <input type="text" name="comic_Name" />
            <small> The name of the comic. (e.g Amazing Spider-Man) </small>

            <label>Comic Publish Year:</label>
            <input type="text" name="comic__Pub_Year" />
            <small> The the year of the comic run (e.g 2018) </small>
            
            <label>Comic Issue:</label>
            <input type="text" name="comic_issue" />
            <small> The issue in the run (e.g. 1) </small>

            <button type="submit">Add a Comic</button>
        </form>
    </body>
</html>