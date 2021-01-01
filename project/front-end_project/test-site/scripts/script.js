const list = document.createElement('ul')
const info = document.createElement('p');
const html = document.querySelector('html')

info.textContent = '下面是一个动态列表。点击列表外任意位置可添加新的列表项。点击已有的列表项可修改内容。'

document.body.appendChild(info);

document.body.appendChild(list);

html.onclick = function (){
    const listItem = document.createElement('li');
    const listContent = prompt('希望列表中是什么内容');
    listItem.textContent = listContent;
    list.appendChild(listItem);

    listItem.onclick = function (e) {
        e.stopPropagation();
        const listContent = prompt('为列表输入新内容');
        this.textContent = listContent;
    };
};