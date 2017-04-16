
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

    var colors =["red","blue"]
    for(var i = 0 ; i < game['teams'].length ; i++)
    {
        team = game['teams'][i];
        for(var j = 0 ; j < team['agents'].length ; j++)
        {
            var agent = team['agents'][j];
            drawAgent(ctx,agent,colors[i]);
        }
    }
    for(var i = 0 ; i < game['obstacles'].length ; i++)
    {
        obstacle = game['obstacles'][i];
        drawObstacle(ctx,obstacle);
    }

}
function drawObstacle(ctx, obstacle) {
    ctx.beginPath();
    ctx.arc(obstacle.x,obstacle.y,obstacle.r,0,2*Math.PI);
    ctx.fillStyle ="grey";
    ctx.fill();
    ctx.stroke();
}
function drawAgent(ctx,agent,color) {
    var r = 10;
    ctx.beginPath();
    ctx.arc(agent.pos.x, agent.pos.y, r, 0, 2 * Math.PI);
    ctx.fillStyle = color;
    ctx.fill();
    ctx.stroke();
    ctx.save();
    ctx.beginPath();
    ctx.lineWidth = "1";
    ctx.strokeStyle = "black"; // Green path
    ctx.moveTo(agent.pos.x, agent.pos.y);
    var xx = agent.pos.x + (r * Math.cos(agent.orientation));
    var yy = agent.pos.y + (r * Math.sin(agent.orientation));
    ctx.lineTo(xx, yy);
    ctx.stroke();
    ctx.restore();
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
