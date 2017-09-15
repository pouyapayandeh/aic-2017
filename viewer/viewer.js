var wsUri = "ws://localhost:8888/";
var output;
var img_cactus;
var img_indian1;
var img_tent;
var img_indian2;
var img_bg;
function init() {
    output = document.getElementById("output");
    img_cactus = document.getElementById("cactus");
    img_indian1 = document.getElementById("indian1");
    img_tent = document.getElementById("tent");
    img_indian2 = document.getElementById("indian2");
    img_bg = document.getElementById("bg");

    testWebSocket();
}
function testWebSocket() {
    websocket = new WebSocket(wsUri);
    websocket.onopen = function (evt) {
        onOpen(evt)
    };
    websocket.onclose = function (evt) {
        onClose(evt)
    };
    websocket.onmessage = function (evt) {
        onMessage(evt)
    };
    websocket.onerror = function (evt) {
        onError(evt)
    };
}
function onOpen(evt) {
    writeToScreen("CONNECTED");
    doSend("WebSocket rocks");
}
function onClose(evt) {
    writeToScreen("DISCONNECTED");
}

function onMessage(evt) {
    // writeToScreen('<span style="color: blue;">RESPONSE: ' + evt.data+'</span>');
//            websocket.close();
    var c = document.getElementById("myCanvas");
    var ctx = c.getContext("2d");
    // ctx.drawImage(img, 10, 10);
    var game = JSON.parse(evt.data);

    var colors = ["red", "blue"];
    c.setAttribute("width",game['width']);
    c.setAttribute("height",game['height']);
    // ctx.clearRect(0, 0, c.width, c.height);
    ctx.drawImage(img_bg,0,0);
    for (var i = 0; i < game['teams'].length; i++) {
        team = game['teams'][i];
        for (var j = 0; j < team['agents'].length; j++) {
            var agent = team['agents'][j];
            drawAgent(ctx, agent, colors[i]);
        }
    }
    for (var i = 0; i < game['obstacles'].length; i++) {
        var obstacle = game['obstacles'][i];
        drawObstacle(ctx, obstacle);
    }
    for (var i = 0; i < game['bullets'].length; i++) {
        var bullet = game['bullets'][i];
        drawBullet(ctx, bullet);
    }
}
function drawObstacle(ctx, obstacle) {
    ctx.beginPath();
    ctx.arc(obstacle.x,obstacle.y,obstacle.r,0,2*Math.PI);
    ctx.fillStyle ="grey";
    ctx.fill();
    ctx.stroke();
    var x, y;

    var pad = (obstacle.r);
    console.log(pad);
    x = obstacle.x - obstacle.r -pad;
    y = obstacle.y - obstacle.r-pad;

    ctx.drawImage(img_cactus, x, y, 2 * (obstacle.r+pad), 2 * (obstacle.r+pad));
}
function drawBullet(ctx, bullet) {
    ctx.beginPath();
    // ctx.arc(bullet.pos.x, bullet.pos.y, 1, 0, 2 * Math.PI);
    ctx.moveTo(bullet.pos.x, bullet.pos.y);
    var xx  = bullet.pos.x + (Math.cos(bullet.orientation)*1000);
    var yy  = bullet.pos.y + (Math.sin(bullet.orientation)*1000);
    ctx.lineTo(xx,yy);
    // ctx.fillStyle = "red";
    // ctx.fill();
    ctx.strokeStyle = "red";
    ctx.stroke();

}
function drawAgent(ctx, agent, color) {
    var r = 10;

    ctx.save();

    var x = agent.pos.x - 2*r;
    var y = agent.pos.y - 2*r;

    ctx.translate(agent.pos.x, agent.pos.y);
    ctx.rotate(agent.orientation - Math.PI/2);

    //ctx.drawImage(img_cactus, x, y, 2 * (obstacle.r+pad), 2 * (obstacle.r+pad));

    if(color == "blue")
        ctx.drawImage(img_indian1,- 2*r,- 2*r, 4 * (r), 4 * (r));
    else
        ctx.drawImage(img_indian2,- 2*r,- 2*r, 4 * (r), 4 * (r));
    ctx.restore();


    // ctx.beginPath();
    // ctx.arc(agent.pos.x, agent.pos.y, r, 0, 2 * Math.PI);
    // ctx.fillStyle = color;
    // ctx.fill();
    // ctx.stroke();
    // ctx.save();
    // ctx.beginPath();
    // ctx.lineWidth = "1";
    // ctx.strokeStyle = "black"; // Green path
    // ctx.moveTo(agent.pos.x, agent.pos.y);
    // var xx = agent.pos.x + (r * Math.cos(agent.orientation));
    // var yy = agent.pos.y + (r * Math.sin(agent.orientation));
    // ctx.lineTo(xx, yy);
    // ctx.stroke();
    // ctx.restore();


}
function onError(evt) {
    writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}
function doSend(message) {
    writeToScreen("SENT: " + message);
    websocket.send(message);
}
function writeToScreen(message) {
    var pre = document.createElement("p");
    pre.style.wordWrap = "break-word";
    pre.innerHTML = message;
    output.appendChild(pre);
}
window.addEventListener("load", init, false);
/**
 * Created by Pouya Payandeh on 4/16/2017.
 */
