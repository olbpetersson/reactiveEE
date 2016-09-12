var initWebsocket = false;

var TweetRow = React.createClass({

    getInitialState: function(){
        return {data: []};
    },

    componentDidMount: function(){
        if(initWebsocket){
            this.initWS();
        } else{
            this.initREST();
        }
    },

    render: function(){
        var tweets = [];
        var tweetData = this.state.data;
        for(var i =0; i < tweetData.length; i++){
            var tweet = tweetData[i];
            var title = tweet.author;
            var body = tweet.message;
            var imageURL = tweet.imageURL;
            tweets.push(<TweetCard title={title} body={body} img={imageURL} key={i}/>);
        }
        return (
            <div className="row">
                {tweets}
            </div>
        );
    },
    initREST: function(){
        var component = this;
        function setComponentState(data){
            component.setState({data: data});
        }
        function callServiceService() {
            $.ajax({
                url: "http://localhost:8080/reactiveEE/api/twitter"
            }).then(function (data) {
                setComponentState(data);
            });
        }
        callServiceService()
    },
    initWS: function(){
        var wsUri = "ws://localhost:8080/reactiveEE/websocket";
        var webSocket;

        console.log("calling opensocket");
        openSocket();
        var component = this;
        function setComponentState(data){
            component.setState({data: data});
        }

        function openSocket(){
            // singleton
            if(webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED){
                console.log("socket already active");
                return;
            }

            webSocket = new WebSocket(wsUri);
            /**
             * Map socket-functions to our socket
             */
            webSocket.onopen = function(event){

                if(event.data === undefined)
                    return;

                webSocket.send("open connection")
            };

            webSocket.onmessage = function(event){
                console.log(event);
                console.log(event.data);
                console.log("got a message via the websocket");
                setComponentState(JSON.parse(event.data));
            };

            webSocket.onclose = function(event){
                console.log("Connection closed");
            };
        }

        function send(){
            webSocket.send("send function websocket js");
        }

        function closeSocket(){
            webSocket.close();
        }
    },
});

var TweetCard = React.createClass({
    render: function(){
        var title = this.props.title;
        var body = this.props.body;
        var img = this.props.img;
        if(!img){
            img = "images/alt.png";
        }
        return (
            <div className="col l6 s6 center-align section">
                <div className="card-panel horizontal valign-wrapper">
                    <div className="card-stacked col s12">
                        <div className="row valign-wrapper">
                            <div className="col s2">
                                <img className="circle responsive-img circle" src={img}/>
                            </div>
                            <div className="col s10">
                                <div className="card-content valign-wrapper">
                                    <p className="flow-text">{body}</p>
                                </div>
                            </div>
                        </div>
                        <div className="card-action">
                            <div className="title-wrapper">
                                - @{title}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );

    }
});

ReactDOM.render(
<TweetRow />,
    document.getElementById("container")
);
