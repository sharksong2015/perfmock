# perfmock
性能测试mock，支持自定义mock文件和延时
java -jar perfmock-0.0.1-SNAPSHOT.jar --mockfile=xxx.json --latency=1000,2000

json文件样例，结构为json数组：
[
{
    "description": "testpost",
    "request" :
        {
        "uri" : "/testpost",
        "method" : "POST",
        "forms" : {"req" : "123"}
        },
    "response" :
        {
		"latency" : 25,
        "json" : {"resultCode":"0000","redirectURL" : "null"}
        }
    },
	
 {
    "description": "test",
    "request" :
        {
        "uri" : "/test",
        "queries" : {"req" : "123"}
        },
    "response" :
        {
		"latency" : 25,
        "json" : {"resultCode":"0000","redirectURL" : "null"}
        }
    }    
	
]
