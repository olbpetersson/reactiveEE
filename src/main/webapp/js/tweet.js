var Row = React.createClass({

    getInitialState: function(){
        return {data: []};
    },

    componentDidMount: function(){
        //this.initREST();
        this.initWS();
    },


    render: function(){
        var tweets = [];
        var tweetData = this.state.data;
        for(var i =0; i < tweetData.length; i++){
            var title = tweetData[i].split(":")[0];
            var body = tweetData[i].substring(tweetData[i].indexOf(":"));
            tweets.push(<TweetCard title={title} body={body} key={tweetData[i]}/>);
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
            component.setState({data: data.split(",")});
        }
        function getTweets() {
            $.ajax({
                url: "http://localhost:27900/reactiveEE/api/twitter"
            }).then(function (data) {
                setComponentState(data);
            });
        }
        getTweets()
    },
    initWS: function(){
        var wsUri = "ws://192.168.1.208:27900/reactiveEE/websocket";
        var webSocket;

        console.log("calling opensocket")
        openSocket();
        var component = this;
        function setComponentState(data){
            component.setState({data: data.split(",")});
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
                setComponentState(event.data);
            };

            webSocket.onclose = function(event){
                webSocket.send("Connection closed");
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
        return (
            <div className="col s4 center-align section">
                <div className="card-small z-depth-3 section blue-grey darken-1 white-text">
                    <span className="card-title flow-text left-align">{title}</span>
                    <div className="card-content">
                        <p>{body}</p>
                    </div>
                </div>
            </div>

        );
    }
});

ReactDOM.render(
<Row />,
    document.getElementById("container")
);
