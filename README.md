# Chat à la Slack

![alt text](https://raw.githubusercontent.com/sguessou/chatalaslack/master/chat_1.PNG)


A simple web chat application built with Clojure(Script).   
This small application doesn't include all of the features expected in a real web chat application, but simply demonstrates the use of websockets for real time messaging in a Clojure(Script) environment.

There are 3 predefined rooms where the users can have a group chat.    
Users are also able to message eachothers privately.    

For a live version go here --> [Chat à la Slack](https://chatalaslack.herokuapp.com/)

## Technologies used in this project

### Backend
[Http-kit](https://www.http-kit.org/) Http server for Clojure. Used for handling HTTP requests and websocket connections.   
[Metosin/Reitit](https://github.com/metosin/reitit) Backend side routing libray for Clojure.    
[Hiccup](https://github.com/weavejester/hiccup) HTML representation library for Clojure(Script).       

### Frontend
[Reagent](https://reagent-project.github.io/) React wrapper for Clojurescript.   
[Re-frame](https://github.com/Day8/re-frame) Client state management library for Clojurescript.    
[Secretary](https://github.com/clj-commons/secretary) client side routing library for Clojurescript.   
[Wscljs](https://github.com/nilenso/wscljs) Lightweight websocket client for Clojurescript.       
[Bulma](https://bulma.io/) CSS framework.   

### Development
[Leiningen](https://leiningen.org/) was used to bootstrap the project with a starting template.    
[Figwheel.main](https://figwheel.org/docs/) is used for frontend side code hot reloading.    
I'm using emacs + cider in my workflow for REPL driven development, but I believe that other editors can be used for the same purpose given that the required plugins are installed.   


## Usage

At the root of the project, launch the following command: 
####  $ lein run 8000.    
Leiningen will download and install all the needed dependencies. After that, you can open your browser and navigate to localhost:8000. 

To run a build for a production frontend bundle run this command: 
#### $ clojure -m figwheel.main -O advanced -bo prod      

## License

Copyright © 2019 FIXME

