<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>Login</title>
    <style>
        body {
            font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
            font-size: 16px;
            line-height: 2;
        }

        div.center {
            width: 800px;
            margin: 0 auto;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="center">
    <h1>Login</h1>

    <form method="post" action="/login">
        <label for="username">Username:</label> &nbsp; <input type="text" id="username" name="username"/> <br/>
        <label for="password">Password:</label> &nbsp; <input type="password" id="password" name="password"/> <br/>
        <input type="submit" value="Login"/>
    </form>
</div>
</body>
</html>