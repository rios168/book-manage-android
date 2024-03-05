## Book Management
###  This system has Java back-end and Android
##### Java  back-end:
		Framework used:  SpringBoot+Mybatis+MySQL+Swagger+Mockito  
		There are two services : DetailService (Provides interface for reading books) and UpdateService(Provides interface for modifying books)  
		And with 2 unit tests, the tests passed  
		The interface request has an interceptor and requires a token to access it  
		Provide Swagger interface documentation  
##### Android:
		The technology used is developed in the Kotlin language, Jetpack Compose UI,Retrofit+OkHttp, Coroutine, Flow, SharedFlow,Navigation  
		The Compose custom component: LimitedNumberTextField, implements the correct input of the restricted year   
		Main ViewModel with unit test, test passed, mock data use Mockk, personally think Mockk is more in line with Kotlin language   
##### CI/CD:   
		The program works by packaging the Jar into Docker-OpenJDK to generate a new docker container "docker(book-service)", and then running the container   
		Use Jenkins to implement automatic package deployment running   
		flow:    
			1 There are 2 ways to trigger a build, manually clicking build and Update the master branch will trigger a build   
			2 After successful build, docker(book-service) will be stopped first.   
			3 Deleting the docker(book-service) Container   
			4 Deleting an image docker(book-service)   
			5 Use Dockerfile to create a new image docker(book-service)   
			6 Run docker(book-service)   
   
This system has been deployed in Ali Cloud, the back-end is running normally, directly download the Android APK installation can be used   
Android APK:   http://8.134.151.83:81/book.apk   

Back-end code:    
		https://gitee.com/riosa/book-manage-backend  (The Dockerfile is in the root directory)   
		https://github.com/rios168/BookJava   
		
Android code:     
		https://gitee.com/riosa/book-manage-android   
		https://github.com/rios168/book-manage-android   
		
Code zip backup address:  http://8.134.151.83:81/book-code-zip.zip   
    
Swagger :   http://8.134.151.83:8000/swagger-ui.html   
mysql://8.134.151.83:3306/book       
Jenkins:     http://8.134.151.83:8080/job/BookManagement-Backend/    
		
		
