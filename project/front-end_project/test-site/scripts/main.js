let myButton = document.querySelector('button');
let myHeading = document.querySelector('h1');
// myHeading.textContent = 'Hello World ---s';


// document.querySelector('h2').onclick = function (){
//     alert('no no no');
// }

// let myImage = document.querySelector('img');
// myImage.onclick = function() {
//     let mySrc = myImage.getAttribute('src');
//     if (mySrc === 'image/backend.png') {
//         myImage.setAttribute('src','image/change.png');
//     } else {
//         myImage.setAttribute('src','image/backend.png')
//     }
// }

function setUserName() {
    let myName = prompt('请输入名字');
    if (!myName || myName === null) {
        setUserName();
    }else {
        localStorage.setItem('name',myName);
        myHeading.textContent = '我最帅 .... ' + myName;
    }
}

if (!localStorage.getItem('name')) {
    setUserName();
} else {
    let storedName = localStorage.getItem('name');
    myHeading.textContent = '我最帅 .... !!!' + storedName;
}

myButton.onclick = function(){
    setUserName();
}