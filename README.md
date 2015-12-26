# CodeReader_for_Github

A simple client for working with Github API.

This repository demonstrate code which allows to understand logic of making HTTP requests for REST web-service and parsing them into POJO classes.

For parsing JSON-responses and making HTTP requests I used a powerful open source library Retrofit 2.0 from Square. This library simplifies communication with REST web-services and increase efficiency of writing code. 

Also, CodeReader for Github is created by me according to Google Material Design. So I tried to make this app as usable as I can. 

First of all you should input your valid login and password of Github account and then (if your credentials are valid and Internet connection is available) click on button LOGIN. 

![alt tag](https://cloud.githubusercontent.com/assets/10655043/12006027/be5e640c-abcb-11e5-9f1d-d0276b8e99b9.png)

After that you can notice basic information about user and repositories. 

In the My Repos you can see all repositories which are created by user. 

![alt tag](https://cloud.githubusercontent.com/assets/10655043/12006020/be2b1944-abcb-11e5-8501-29588777bc8a.png)

All of repositories which are forked by user are displayed in the Forks tab.
 
 ![alt tag](https://cloud.githubusercontent.com/assets/10655043/12006021/be2d4976-abcb-11e5-84ed-f47adf62d4b7.png)
 
Also there is tab Public Activity for displaying recent user actions.  
Note: Tab PUBLIC ACTIVITY is not finished in this version of the app.

General information about user locates in Drawer Menu (click on burger icon). 
So, here you can see user avatar, count of followings and followers and date of creating Github accout. For downloading user avatar from the Internet I used library Picaso which is also created by Square.

Also here is a list with actions. For creating this list I used Recycler View because it is much more friendly for using device resourses than ListView.  

![alt tag](https://cloud.githubusercontent.com/assets/10655043/12006022/be335d84-abcb-11e5-9dcb-26bd4ecb937a.png)

Note: List of actions is not finished now. It will be able in the next versions of this app. 

After clicking on a repository a new activity is started. 
Here are tree tabs. 

In the first one you can navigate inside files of the repository and also you can choose the branch. So it allows to discover all files in all branches of repository.   

![alt tag](https://cloud.githubusercontent.com/assets/10655043/12006025/be4791be-abcb-11e5-8ca3-f1e8e4ce4a2c.png)

In the second tab of this activity you can see all commit of the branch which you can choose in the drop down list (spinner). 

![alt tag](https://cloud.githubusercontent.com/assets/10655043/12006023/be388b4c-abcb-11e5-9ce8-22779015d567.png)

The third tab represents a list of all branches in the repository.

![alt tag](https://cloud.githubusercontent.com/assets/10655043/12006024/be3f8406-abcb-11e5-92eb-53824a34d91a.png)

So, here is the basic version of my prototype for working with Github API. In the next versions I will add new features such as public activities o of the user, possibility to search Github users and repositories.   



