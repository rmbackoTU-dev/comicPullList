<html>
    <head>Comic Book Pull List--Result</head>
    <body>
        <h2>The result of your comic entry</h2>
        <% String comicName=response.getAttribute(comic_Name) %>
        <% String comicYear=response.getAttribute(comic_Pub_Year) %>
        <% String comicIssue=response.getAttribute(comic_issue) %>
        <p>Your comic name is: <%= comicName %> </p>
        <p>Your comic run year is: <%= comicYear %> </p>
        <p>Your comic Issue is: <%= comicIssue %> </p>
    </body>
</html>