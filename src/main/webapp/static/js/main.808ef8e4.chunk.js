(this.webpackJsonpnotespage=this.webpackJsonpnotespage||[]).push([[0],{14:function(e,t,n){e.exports=n(37)},36:function(e,t,n){},37:function(e,t,n){"use strict";n.r(t);var a=n(0),l=n.n(a),c=n(13),r=n.n(c),o=n(3),u=n(2),i=n.n(u),m="http://192.168.1.100:8081/api/notes",s=function(){return i.a.get(m).then((function(e){return e.data}))},f=function(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:10,t=i.a.get("".concat(m,"/latest/").concat(e));return t.then((function(e){return e.data}))},E=function(){return i.a.get("".concat(m,"/categories")).then((function(e){return e.data}))},g=function(e){return i.a.post(m,e).then((function(e){return e.data}))},v=function(){var e=Object(a.useState)([]),t=Object(o.a)(e,2),n=t[0],c=t[1],r=Object(a.useState)("10"),u=Object(o.a)(r,2),i=u[0],m=u[1],v=Object(a.useState)(""),p=Object(o.a)(v,2),b=p[0],h=p[1],d=Object(a.useState)("default"),O=Object(o.a)(d,2),j=O[0],y=O[1],S=Object(a.useState)([""]),N=Object(o.a)(S,2),C=N[0],k=N[1];Object(a.useEffect)((function(){"all"===i?s().then((function(e){c(e)})):f(i).then((function(e){c(e)}))}),[i]),Object(a.useEffect)((function(){E().then((function(e){k(e)}))}),[]);var D=function(e){y(e.target.value)},w=function(e){m(e.target.value)},T=function(){return l.a.createElement("div",{className:"note-heading"},l.a.createElement("small",null,"Select number of notes to grab "),l.a.createElement("select",{value:i,onChange:w},l.a.createElement("option",{value:"5"},"5"),l.a.createElement("option",{value:"10"},"10"),l.a.createElement("option",{value:"20"},"20"),l.a.createElement("option",{value:"50"},"50"),l.a.createElement("option",{value:"all"},"all")))},x=function(e){console.log(e);var t=e.note.category,n=e.note.creationDateTime;return l.a.createElement("div",{className:"note-card"},l.a.createElement("p",null,l.a.createElement("small",null,l.a.createElement("b",null,"Category:")," ",t," | ",l.a.createElement("b",null,"Created:")," ",n.monthValue,"/",n.dayOfMonth,"/",n.year," @",n.hour,":",n.minute,":",n.second)),l.a.createElement("p",null,e.note.entry))},I=function(){return l.a.createElement("small",null,"Select a category",l.a.createElement("select",{value:j,onChange:D},C.map((function(e){return l.a.createElement("option",{value:e,key:e},e)}))))};return document.title="Project 0 Notes",l.a.createElement(l.a.Fragment,null,l.a.createElement("h1",null,"Notes"),l.a.createElement(T,null),l.a.createElement("div",{className:"note-list"},n.map((function(e){return l.a.createElement(x,{key:e.id,note:e})}))),l.a.createElement("div",{className:"entryarea"},l.a.createElement("form",{onSubmit:function(e){var t={entry:b,category:j,creationDateTime:(new Date).toISOString().replace("T"," ").slice(0,19)};console.log(t),g(t).then((function(e){console.log(e),h("")}))}},l.a.createElement("small",null,"Enter note:"),l.a.createElement("br",null),l.a.createElement("textarea",{value:b,onChange:function(e){e.preventDefault(),h(e.target.value)}}),l.a.createElement(I,null),l.a.createElement("br",null),l.a.createElement("button",{type:"submit"},"Save"))))};n(36);r.a.render(l.a.createElement(v,null),document.getElementById("root"))}},[[14,1,2]]]);
//# sourceMappingURL=main.808ef8e4.chunk.js.map