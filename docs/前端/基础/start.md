# 前端基础

## 一.确定前端存储地址

1. 确定网站项目储存位置。在该位置下创建一个文件夹（比如 `web-projects`）。所有网站项目都存在一处。
2. 在这个文件夹里再新建一个文件夹（比如 `test-site`，读者可自由发挥想象），来存放第一个网站。

## 二.基本项目结构及含义

1. `**index.html**` ：这个文件一般包含主页内容，即用户第一次访问站点时看到的文本和图像。使用文本编辑器在 `test-site` 文件夹中新建 `index.html`。
2. **`images` 文件夹** ：这个文件夹包含站点中的所有图像。在 `test-site` 文件夹中新建 `images` 文件夹。
3. **`styles` 文件夹** ：这个文件夹包含站点所需样式表（比如，设置文本颜色和背景颜色）。在 `test-site` 文件夹中新建一个 `styles` 文件夹。
4. **`scripts` 文件夹** ：这个文件夹包含提供站点交互功能的 JavaScript 代码（比如读取数据的按钮）。在 `test-site` 文件夹中新建一个 `scripts` 文件夹。

**注：**我们将`script`放在HTML文件的底部附近的原因是浏览器会按照代码在文件中的顺序加载 HTML。如果先加载的 JavaScript 期望修改其下方的 HTML，那么它可能由于 HTML 尚未被加载而失效。因此，将 JavaScript 代码放在 HTML页面的底部附近通常是最好的策略。

## 三. JavaScript类型
#### 1. Undefined,Null
Undefined 类型表示未定义，它的类型只有一个值，就是 undefined。任何变量在赋值前是 Undefined 类型、值为 undefined，一般我们可以用全局变量 undefined（就是名为 undefined 的这个变量）来表达这个值，或者 void 运算来把任意一个表达式变成 undefined 值。
> 为什么建议使用 void 0 代替 undefined?
 因为 JavaScript 的代码 undefined 是一个变量，而并非是一个关键字，这是 JavaScript 语言公认的设计失误之一，所以，我们为了避免无意中被篡改，我建议使用 void 0 来获取 undefined 值

> null和undefined的区别：
>1. 定义方面：null表示定义了但是为空，undefined表示为定义；
>2. 语法方面：null是一个关键字，也是一个值，undefined不是关键字，是一个值 
>3. typeof null是Object,typeof undefined 是undefined 
>4. null转成Number是0；undefined转成number是NaN

#### 2. Boolean

#### 3. String

String 有最大长度是 2^53 - 1.因为 String 的意义并非“字符串”,而是字符串的 UTF16 编码,所以,字符串的最大长度，实际上是受字符串的编码长度影响的。
JavaScript 中的字符串是永远无法变更的，一旦字符串构造出来，无法用任何方式改变字符串的内容

#### 4. Number
JavaScript 中的 Number 类型有  2^64-2^53+3个值。
> 特殊值 : 
>NaN，占用了 9007199254740990，这原本是符合 IEEE 规则的数字；
>Infinity，无穷大；
>-Infinity，负无穷大。

> 为什么 0.1+0.2 不能 =0.3 ? 
根据双精度浮点数的定义，Number 类型中有效的整数范围是 -0x1fffffffffffff 至 0x1fffffffffffff，所以 Number 无法精确表示此范围外的整数 , 所以错误的实际上不是不相等,而是比较方式


```javascript
// 错误比较
  console.log( 0.1 + 0.2 == 0.3);
// 正确比较
  console.log( Math.abs(0.1 + 0.2 - 0.3) <= Number.EPSILON);
```

#### 5. Symbol

#### 6. Object

## 四. JavaScript对象
宿主对象（host Objects）：由 JavaScript 宿主环境提供的对象，它们的行为完全由宿主环境决定。
内置对象（Built-in Objects）：由 JavaScript 语言提供的对象。
固有对象（Intrinsic Objects ）：由标准规定，随着 JavaScript 运行时创建而自动创建的对象实例。
原生对象（Native Objects）：可以由用户通过 Array、RegExp 等内置构造器或者特殊语法创建的对象。
普通对象（Ordinary Objects）：由{}语法、Object 构造器或者 class 关键字定义类创建的对象，它能够被原型继承。


npm 换源

https://www.jianshu.com/p/f311a3a155ff