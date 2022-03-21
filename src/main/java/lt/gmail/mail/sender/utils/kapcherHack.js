// http://ejohn.org/blog/ocr-and-neural-nets-in-javascript/

// Atpazysta nesudetinga texta, https://github.com/naptha/tesseract.js#tesseractjs

var cropped_canvas;

// convertuoja duomenis i pilka spava
function convert_grey(image_data){
  for (var x = 0; x < image_data.width; x++){
    for (var y = 0; y < image_data.height; y++){
      var i = x*4+y*4*image_data.width;
      var luma = Math.floor(image_data.data[i] * 299/1000 +
        image_data.data[i+1] * 587/1000 +
        image_data.data[i+2] * 114/1000);

      image_data.data[i] = luma;
      image_data.data[i+1] = luma;
      image_data.data[i+2] = luma;
      image_data.data[i+3] = 255;
    }
  }
  return image_data;
}

// perduota spalva pavercia i balta o visa kita i juoda.
function filter(image_data, colour){
  for (var x = 0; x < image_data.width; x++){
    for (var y = 0; y < image_data.height; y++){
      var i = x*4+y*4*image_data.width;

      // Turn all the pixels of the certain colour to white
      if (image_data.data[i] == colour) {
        image_data.data[i] = 255;
        image_data.data[i+1] = 255;
        image_data.data[i+2] = 255;

      // Everything else to black
      } else {
        image_data.data[i] = 0;
        image_data.data[i+1] = 0;
        image_data.data[i+2] = 0;
      }
    }
  }
  return image_data;
}

filter(image_data[0], 105);
filter(image_data[1], 120);
filter(image_data[2], 135);

var i = x*4+y*4*image_data.width;
var above = x*4+(y-1)*4*image_data.width;
var below = x*4+(y+1)*4*image_data.width;

if (image_data.data[i] == 255 &&
    image_data.data[above] == 0 &&
    image_data.data[below] == 0)  {
  image_data.data[i] = 0;
  image_data.data[i+1] = 0;
  image_data.data[i+2] = 0;
}

cropped_canvas.getContext("2d").fillRect(0, 0, 20, 25);

var edges = find_edges(image_data[i]);

cropped_canvas.getContext("2d").drawImage(canvas, edges[0], edges[1],
  edges[2]-edges[0], edges[3]-edges[1], 0, 0,
  edges[2]-edges[0], edges[3]-edges[1]);

image_data[i] = cropped_canvas.getContext("2d").getImageData(0, 0, cropped_canvas.width, cropped_canvas.height);

var topPosition = document.getElementById('62382f795e243').offsetTop;
var leftPosition = document.getElementById('62382f795e243').offsetLeft;
var x = document.createElement("CANVAS");

function copy() {
  var imgData = ctx.getImageData(10, 10, 50, 50);
  ctx.putImageData(imgData, 10, 70);
}

function myCanvas(imgId) {
  var x = document.createElement("CANVAS");
  var ctx = x.getContext("2d");
  ctx.fillStyle = "#FF0000";
  ctx.fillRect(20, 20, 150, 100);
  x.id = 'myId_canvas_22';
  document.body.appendChild(x);
  var img = document.getElementById(imgId);
  ctx.drawImage(img, 0, 0);
  return x;
}
 var s1 = myCanvas('62382f795e243');
 var imageDate = s1.getContext("2d").getImageData(10, 10, 50, 50);
 s1.getContext("2d").putImageData(convert_grey(imageDate), 0, 0);
