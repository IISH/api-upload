<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>ZIP upload</title>
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

        span.error {
            display: block;
            color: red;
            font-weight: bold;
            margin-bottom: 10px;
        }

        span.success {
            display: block;
            color: green;
            font-weight: bold;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
<div class="center">
    <h1>Upload ZIP file</h1>

    <#if error??>
        <span class="error">
            ${error}
        </span>
    </#if>

    <#if success??>
        <span class="success">
            ${success}
        </span>
    </#if>

    <form method="post" action="" enctype="multipart/form-data">
        <input type="file" name="file"/> <br/>
        <button type="submit">Upload</button>
    </form>
</div>
</body>
</html>