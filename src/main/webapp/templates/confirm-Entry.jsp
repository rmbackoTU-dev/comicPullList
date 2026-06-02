<html>
    <head>Comic Book Pull List--Result</head>
    <body>
        <h2>The result of your comic entry</h2>
        <% String comicName=(String) request.getAttribute("name"); %>
        <% String comicYear=(String) request.getAttribute("pubYear"); %>
        <% String comicIssue=(String) request.getAttribute("issue"); %>
        <p>Your comic name is: <%= comicName %> </p>
        <p>Your comic run year is: <%= comicYear %> </p>
        <p>Your comic Issue is: <%= comicIssue %> </p>
    </body>
</html>