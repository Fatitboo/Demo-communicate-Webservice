const express = require('express')
const app = express()
var bodyParser = require('body-parser');
var multer = require('multer');
var upload = multer();
const cors = require('cors');
app.use(bodyParser.json()); 

// for parsing application/xwww-
app.use(bodyParser.urlencoded({ extended: true })); 
//form-urlencoded

// for parsing multipart/form-data
app.use(upload.array()); 
app.use(express.static('public'));
app.use(cors());
app.get('/', (req, res)=>{
    console.log('ok')

    res.json('hello world')
})
app.post('/api', (req, res)=>{
    console.log({...req?.body})
    res.json({'result':(parseInt(req?.body?.st1)+parseInt(req?.body?.st2)).toString()})
    
})

app.listen(3001,()=>{console.log('Server started on port 3001.')} )