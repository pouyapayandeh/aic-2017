
var wsUri = "ws://localhost:8888/";
var output;
function init()
{
    output = document.getElementById("output");
    testWebSocket();
}
function testWebSocket()
{
    websocket = new WebSocket(wsUri);
    websocket.onopen = function(evt) { onOpen(evt) };
    websocket.onclose = function(evt) { onClose(evt) };
    websocket.onmessage = function(evt) { onMessage(evt) };
    websocket.onerror = function(evt) { onError(evt) };
}
function onOpen(evt)
{
    writeToScreen("CONNECTED");
    doSend("WebSocket rocks");
}
function onClose(evt)
{
    writeToScreen("DISCONNECTED");
}
function onMessage(evt)
{
    // writeToScreen('<span style="color: blue;">RESPONSE: ' + evt.data+'</span>');
//            websocket.close();
    var c = document.getElementById("myCanvas");
    var ctx = c.getContext("2d");
    var game = JSON.parse(evt.data);
    ctx.clearRect(0,0,c.width,c.height);
    for(var i = 0 ; i < game['teams'].length ; i++)
    {
        team = game['teams'][i];
        for(var j = 0 ; j < team['agents'].length ; j++)
        {
            var agent = team['agents'][j];
            ctx.beginPath();

            ctx.arc(agent.pos.x,agent.pos.y,40,0,2*Math.PI);
            ctx.fillStyle = "red";
            ctx.fill();
            ctx.stroke();
        }
    }

}
function onError(evt)
{
    writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}
function doSend(message)
{
    writeToScreen("SENT: " + message);
    websocket.send(message);
}
function writeToScreen(message)
{
    var pre = document.createElement("p");
    pre.style.wordWrap = "break-word";
    pre.innerHTML = message;
    output.appendChild(pre);
}
window.addEventListener("load", init, false);
/**
 * Created by Pouya Payandeh on 4/16/2017.
 */
