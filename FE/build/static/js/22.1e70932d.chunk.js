(this["webpackJsonpmatx-react"]=this["webpackJsonpmatx-react"]||[]).push([[22],{1291:function(e,t,n){},1437:function(e,t,n){"use strict";n.r(t),n.d(t,"default",(function(){return _}));var i=n(10),a=n(13),c=n(1),l=n(218),r=n(211),s=n(97),o=n(640),d=n.n(o),u=n(118),j=n(175),h=j.a+"/api/recruits",b=function(e){return u.a.post(h,e)},x=function(e){return u.a.put(h+"/"+e.id,e)},m=n(380),O=n(645),v=n.n(O),g=n(644),f=n.n(g),p=n(632),y=n.n(p),C=n(96),T=n(387),k=n(388),w=n(906),S=n(1045),R=n(219),M=n.n(R),V=n(598),W=n(676),z=n(2);function A(e){var t=e.open,n=e.handleClose,s=e.item,o=Object(c.useState)({}),d=Object(a.a)(o,2),u=d[0],j=d[1];Object(c.useEffect)((function(){j(s)}),[]);return Object(z.jsxs)(T.a,{open:t,fullWidth:!0,maxWidth:"md",children:[Object(z.jsxs)(S.a,{style:{marginBlockEnd:0,padding:"16px 24px 0"},children:[null!==u&&void 0!==u&&u.id?"S\u1eeda k\u1ebf ho\u1ea1ch":"Th\xeam k\u1ebf ho\u1ea1ch",Object(z.jsx)(l.a,{className:"icon-close",onClick:n,children:Object(z.jsx)(m.a,{color:"error",children:Object(z.jsx)(M.a,{})})})]}),Object(z.jsxs)(W.ValidatorForm,{onSubmit:function(){u.id?1===u.status?x(u).then((function(e){C.b.success("L\u01b0u k\u1ebf ho\u1ea1ch th\xe0nh c\xf4ng"),n()})).catch((function(e){return C.b.error("C\xf3 l\u1ed7i x\u1ea3y ra!")})):2===u.status&&x(u).then((function(e){C.b.success("G\u1eedi y\xeau c\u1ea7u ph\xea duy\u1ec7t th\xe0nh c\xf4ng"),n()})).catch((function(e){return C.b.error("C\xf3 l\u1ed7i x\u1ea3y ra!")})):1===u.status?b(u).then((function(e){200===e.data.statusCode?(C.b.success("Th\xeam k\u1ebf ho\u1ea1ch th\xe0nh c\xf4ng"),n()):C.b.warning(e.data.message)})).catch((function(e){return C.b.error("C\xf3 l\u1ed7i x\u1ea3y ra!")})):2===u.status&&b(u).then((function(e){200===e.data.statusCode?(C.b.success("G\u1eedi y\xeau c\u1ea7u duy\u1ec7t th\xe0nh c\xf4ng"),n()):C.b.warning(e.data.message)})).catch((function(e){return C.b.error("C\xf3 l\u1ed7i x\u1ea3y ra!")}))},onError:function(e){return console.log(e)},children:[Object(z.jsx)(w.a,{style:{padding:"0 20px"},children:Object(z.jsxs)(V.a,{container:!0,spacing:2,style:{marginTop:5},children:[Object(z.jsxs)(V.a,{container:!0,item:!0,xs:12,md:12,spacing:2,children:[Object(z.jsx)(V.a,{item:!0,xs:4,md:4,children:Object(z.jsx)(W.TextValidator,{label:"M\xe3 k\u1ebf ho\u1ea1ch",variant:"outlined",fullWidth:!0,value:null===u||void 0===u?void 0:u.code,onChange:function(e){return j(Object(i.a)(Object(i.a)({},u),{},{code:e.target.value}))},validators:["required","matchRegexp:^MaTD[0-9]{4}$"],errorMessages:["Vui l\xf2ng nh\u1eadp tr\u01b0\u1eddng n\xe0y","M\xe3 k\u1ebf ho\u1ea1ch ch\u01b0a \u0111\xfang format VD:(MaTD9999)"]})}),Object(z.jsx)(V.a,{item:!0,xs:5,md:5,children:Object(z.jsx)(W.TextValidator,{label:"T\xean k\u1ebf ho\u1ea1ch",variant:"outlined",fullWidth:!0,value:null===u||void 0===u?void 0:u.titleRecruit,onChange:function(e){return j(Object(i.a)(Object(i.a)({},u),{},{titleRecruit:e.target.value}))},validators:["required","matchRegexp:^[0-9a-zA-Z].{5,100}$"],errorMessages:["Vui l\xf2ng nh\u1eadp tr\u01b0\u1eddng n\xe0y","T\xean k\u1ebf ho\u1ea1ch ph\u1ea3i l\u1edbn h\u01a1n 5 k\xed t\u1ef1 v\xe0 nh\u1ecf h\u01a1n 100 k\xed t\u1ef1"]})}),Object(z.jsx)(V.a,{item:!0,xs:3,md:3,children:Object(z.jsx)(W.TextValidator,{label:"S\u1ed1 l\u01b0\u1ee3ng tuy\u1ec3n d\u1ee5ng",type:"number",variant:"outlined",fullWidth:!0,value:null===u||void 0===u?void 0:u.quantity,onChange:function(e){return j(Object(i.a)(Object(i.a)({},u),{},{quantity:e.target.value}))},validators:["required"],errorMessages:["Vui l\xf2ng nh\u1eadp tr\u01b0\u1eddng n\xe0y"]})})]}),Object(z.jsx)(V.a,{item:!0,xs:12,md:12,children:Object(z.jsx)(W.TextValidator,{label:"\u0110\xe3i ng\u1ed9",variant:"outlined",fullWidth:!0,multiline:!0,minRows:2,maxRows:5,value:null===u||void 0===u?void 0:u.benefitsReceived,onChange:function(e){return j(Object(i.a)(Object(i.a)({},u),{},{benefitsReceived:e.target.value}))},validators:["required"],errorMessages:["Vui l\xf2ng nh\u1eadp tr\u01b0\u1eddng n\xe0y"]})}),Object(z.jsx)(V.a,{item:!0,xs:12,md:12,children:Object(z.jsx)(W.TextValidator,{label:"Y\xeau c\u1ea7u tuy\u1ec3n d\u1ee5ng",variant:"outlined",fullWidth:!0,multiline:!0,minRows:2,maxRows:5,value:null===u||void 0===u?void 0:u.requireRecruit,onChange:function(e){return j(Object(i.a)(Object(i.a)({},u),{},{requireRecruit:e.target.value}))},validators:["required"],errorMessages:["Vui l\xf2ng nh\u1eadp tr\u01b0\u1eddng n\xe0y"]})}),Object(z.jsx)(V.a,{item:!0,xs:12,md:12,children:Object(z.jsx)(W.TextValidator,{label:"M\xf4 t\u1ea3",multiline:!0,minRows:4,maxRows:10,variant:"outlined",fullWidth:!0,value:null===u||void 0===u?void 0:u.description,onChange:function(e){return j(Object(i.a)(Object(i.a)({},u),{},{description:e.target.value}))},validators:["required"],errorMessages:["Vui l\xf2ng nh\u1eadp tr\u01b0\u1eddng n\xe0y"]})}),Object(z.jsx)(V.a,{item:!0,xs:12,md:12,children:Object(z.jsx)(W.TextValidator,{label:"K\xeanh tuy\u1ec3n d\u1ee5ng",variant:"outlined",fullWidth:!0,multiline:!0,minRows:1,value:null===u||void 0===u?void 0:u.recruitmentChannel,onChange:function(e){return j(Object(i.a)(Object(i.a)({},u),{},{recruitmentChannel:e.target.value}))},validators:["required"],errorMessages:["Vui l\xf2ng nh\u1eadp tr\u01b0\u1eddng n\xe0y"]})})]})}),Object(z.jsx)(k.a,{children:Object(z.jsxs)(z.Fragment,{children:[Object(z.jsx)(r.a,{variant:"contained",color:"secondary",onClick:n,children:"H\u1ee7y"}),Object(z.jsx)(r.a,{type:"submit",variant:"contained",color:"primary",onClick:function(){return j(Object(i.a)(Object(i.a)({},u),{},{status:2}))},children:"L\u01b0u v\xe0 g\u1eedi"}),Object(z.jsx)(r.a,{type:"submit",variant:"contained",color:"primary",onClick:function(){return j(Object(i.a)(Object(i.a)({},u),{},{status:1}))},children:"L\u01b0u nh\xe1p"})]})})]})]})}var L=n(223),q=n(591);function E(e){var t=e.open,n=e.handleClose,i=e.item;return Object(z.jsxs)(T.a,{open:t,fullWidth:!0,maxWidth:"md",children:[Object(z.jsxs)(S.a,{style:{marginBlockEnd:0,padding:"16px 24px 0"},children:["Th\xf4ng tin k\u1ebf ho\u1ea1ch",Object(z.jsx)(l.a,{className:"icon-close",onClick:n,children:Object(z.jsx)(m.a,{color:"error",children:Object(z.jsx)(M.a,{})})})]}),Object(z.jsx)(w.a,{style:{padding:"0 20px"},children:Object(z.jsxs)(V.a,{container:!0,spacing:2,children:[Object(z.jsx)(V.a,{container:!0,item:!0,xs:12,md:12,justifyContent:"center",children:Object(z.jsx)("h2",{children:null===i||void 0===i?void 0:i.titleRecruit})}),Object(z.jsxs)(V.a,{container:!0,item:!0,xs:3,md:3,spacing:1,children:[Object(z.jsx)(V.a,{item:!0,className:"fw-600",children:"M\xe3 k\u1ebf ho\u1ea1ch:"}),Object(z.jsx)(V.a,{item:!0,xs:6,md:6,children:null===i||void 0===i?void 0:i.code})]}),Object(z.jsxs)(V.a,{container:!0,item:!0,xs:3,md:3,spacing:1,children:[Object(z.jsx)(V.a,{item:!0,className:"fw-600",children:"S\u1ed1 l\u01b0\u1ee3ng tuy\u1ec3n d\u1ee5ng:"}),Object(z.jsx)(V.a,{item:!0,xs:1,md:1,children:null===i||void 0===i?void 0:i.quantity})]}),Object(z.jsxs)(V.a,{container:!0,item:!0,xs:6,md:6,spacing:1,children:[Object(z.jsx)(V.a,{item:!0,className:"fw-600",children:"Tr\u1ea1ng th\xe1i k\u1ebf ho\u1ea1ch:"}),Object(z.jsx)(V.a,{item:!0,xs:5,md:5,children:Object(j.b)(null===i||void 0===i?void 0:i.status).message})]}),(null===i||void 0===i?void 0:i.feedback)&&Object(z.jsxs)(V.a,{container:!0,item:!0,spacing:1,children:[Object(z.jsx)(V.a,{item:!0,className:"fw-600",children:"Y\xeau c\u1ea7u ch\u1ec9nh s\u1eeda:"}),Object(z.jsx)(V.a,{item:!0,xs:10,children:null===i||void 0===i?void 0:i.feedback})]}),Object(z.jsxs)(V.a,{container:!0,item:!0,spacing:1,children:[Object(z.jsx)(V.a,{item:!0,xs:12,md:12,children:Object(z.jsx)("span",{className:"fz-18 fw-600",children:"M\xf4 t\u1ea3 c\xf4ng vi\u1ec7c"})}),Object(z.jsx)(V.a,{item:!0,xs:12,md:12,children:Object(z.jsx)(q.a,{fullWidth:!0,multiline:!0,variant:"standard",value:null===i||void 0===i?void 0:i.description,InputProps:{readOnly:!0,disableUnderline:!0}})})]}),Object(z.jsxs)(V.a,{container:!0,item:!0,spacing:1,children:[Object(z.jsx)(V.a,{item:!0,xs:12,md:12,children:Object(z.jsx)("span",{className:"fz-18 fw-600",children:"Y\xeau c\u1ea7u \u1ee9ng vi\xean"})}),Object(z.jsx)(V.a,{item:!0,xs:12,md:12,children:Object(z.jsx)(q.a,{fullWidth:!0,multiline:!0,variant:"standard",value:null===i||void 0===i?void 0:i.requireRecruit,InputProps:{readOnly:!0,disableUnderline:!0}})})]}),Object(z.jsxs)(V.a,{container:!0,item:!0,spacing:1,children:[Object(z.jsx)(V.a,{item:!0,xs:12,md:12,children:Object(z.jsx)("span",{className:"fz-18 fw-600",children:"Quy\u1ec1n l\u1ee3i"})}),Object(z.jsx)(V.a,{item:!0,xs:12,md:12,children:Object(z.jsx)(q.a,{fullWidth:!0,multiline:!0,variant:"standard",value:null===i||void 0===i?void 0:i.benefitsReceived,InputProps:{readOnly:!0,disableUnderline:!0}})})]}),Object(z.jsxs)(V.a,{container:!0,item:!0,spacing:1,children:[Object(z.jsx)(V.a,{item:!0,xs:12,md:12,children:Object(z.jsx)("span",{className:"fz-18 fw-600",children:"C\xe1c k\xeanh tuy\u1ec3n d\u1ee5ng"})}),Object(z.jsx)(V.a,{item:!0,xs:12,md:12,children:Object(z.jsx)(q.a,{fullWidth:!0,multiline:!0,variant:"standard",value:null===i||void 0===i?void 0:i.recruitmentChannel,InputProps:{readOnly:!0,disableUnderline:!0}})})]})]})}),Object(z.jsx)(k.a,{children:Object(z.jsx)(r.a,{variant:"contained",color:"secondary",onClick:n,children:"\u0110\xf3ng"})})]})}var N=n(633),P=n.n(N),D=n(1026);n(1291);function _(){var e=Object(c.useState)([]),t=Object(a.a)(e,2),n=t[0],o=t[1],b=Object(c.useState)(!1),x=Object(a.a)(b,2),O=x[0],g=x[1],p=Object(c.useState)(!1),T=Object(a.a)(p,2),k=T[0],w=T[1],S=Object(c.useState)(!1),R=Object(a.a)(S,2),M=R[0],W=R[1],N=Object(c.useState)({}),_=Object(a.a)(N,2),H=_[0],I=_[1],Y=Object(c.useState)(!1),B=Object(a.a)(Y,2),F=B[0],U=B[1],X=Object(c.useState)(""),K=Object(a.a)(X,2),G=K[0],J=K[1],$=[{title:"STT",field:"STT",render:function(e){return e.tableData.id+1},cellStyle:{width:"3%",textAlign:"center"}},{title:"Thao t\xe1c",field:"action",render:function(e){return Object(z.jsxs)(z.Fragment,{children:[Object(z.jsx)(m.a,{color:"primary",onClick:function(){w(!0),I(e)},children:Object(z.jsx)(y.a,{})}),Object(z.jsx)(m.a,{color:"success",onClick:function(){g(!0),I(e)},disabled:6===(null===e||void 0===e?void 0:e.status)||4===(null===e||void 0===e?void 0:e.status)||5===(null===e||void 0===e?void 0:e.status),children:Object(z.jsx)(f.a,{})}),Object(z.jsx)(m.a,{color:"error",onClick:function(){W(!0),I(e)},disabled:6===(null===e||void 0===e?void 0:e.status)||4===(null===e||void 0===e?void 0:e.status)||5===(null===e||void 0===e?void 0:e.status)||10===(null===e||void 0===e?void 0:e.status),children:Object(z.jsx)(v.a,{})})]})},cellStyle:{width:"10%",textAlign:"center"}},{title:"T\xean k\u1ebf ho\u1ea1ch",field:"name",render:function(e){return e.titleRecruit},cellStyle:{width:"10%",textAlign:"left"},headerStyle:{textAlign:"left"}},{title:"S\u1ed1 l\u01b0\u1ee3ng",field:"quantity",render:function(e){return e.quantity},cellStyle:{width:"5%",textAlign:"center"},headerStyle:{textAlign:"center"}},{title:"C\xe1c k\xeanh tuy\u1ec3n d\u1ee5ng ch\xednh",field:"description",render:function(e){return e.recruitmentChannel},headerStyle:{textAlign:"center"},cellStyle:{width:"20%",textOverflow:"ellipsis",textAlign:"center",whiteSpace:"nowrap",overflow:"hidden",maxWidth:100}},{title:"Tr\u1ea1ng th\xe1i",field:"status",render:function(e){var t=Object(j.b)(e.status).message,n=Object(j.b)(e.status).color;return Object(z.jsx)("div",{className:n,children:t})},cellStyle:{width:"10%",textAlign:"left"},headerStyle:{textAlign:"left"}}];Object(c.useEffect)((function(){U(!0),Q()}),[G]);var Q=function(){u.a.get(h).then((function(e){200===e.data.statusCode?(U(!1),null!==G&&void 0!==G&&G.value?o(e.data.data.filter((function(e){return e.status===(null===G||void 0===G?void 0:G.value)}))):o(e.data.data.filter((function(e){return 3!==e.status&&2!==e.status})))):(U(!1),C.b.warning("L\u1ed7i x\xe1c th\u1ef1c!"))})).catch((function(e){C.b.error("C\xf3 l\u1ed7i x\u1ea3y ra!"),U(!1)}))};console.log(n);var Z=function(){g(!1),W(!1),w(!1),I({}),Q()};return Object(z.jsxs)(z.Fragment,{children:[Object(z.jsxs)(l.a,{style:{margin:20},children:[Object(z.jsx)(s.a,{routeSegments:[{name:"Tuy\u1ec3n d\u1ee5ng",path:"/plan"},{name:"K\u1ebf ho\u1ea1ch tuy\u1ec3n d\u1ee5ng"}]}),Object(z.jsxs)(V.a,{container:!0,spacing:1,justifyContent:"space-between",children:[Object(z.jsx)(V.a,{item:!0,xs:9,children:Object(z.jsx)(r.a,{variant:"contained",color:"primary",size:"medium",style:{margin:"20px 0",padding:"5px 20px"},onClick:function(){return g(!0)},children:"Th\xeam"})}),Object(z.jsxs)(V.a,{item:!0,container:!0,xs:3,children:[Object(z.jsx)(V.a,{item:!0,xs:10,children:Object(z.jsx)(D.a,{options:j.g,getOptionLabel:function(e){return(null===e||void 0===e?void 0:e.name)||""},value:G,onChange:function(e,t){return J(t)},componentsProps:{paper:{elevation:8}},fullWidth:!0,renderInput:function(e){return Object(z.jsx)(q.a,Object(i.a)(Object(i.a)({},e),{},{variant:"standard",fullWidth:!0,label:"L\u1ecdc theo tr\u1ea1ng th\xe1i",size:"small"}))}})}),Object(z.jsx)(V.a,{item:!0,xs:2,children:Object(z.jsx)(m.a,{color:"primary",onClick:function(){Q()},children:Object(z.jsx)(P.a,{})})})]})]}),Object(z.jsx)(d.a,{title:"Danh s\xe1ch k\u1ebf ho\u1ea1ch",columns:$,data:n,options:{sorting:!1,maxBodyHeight:"60vh",pageSize:10,pageSizeOptions:[10,20,50],draggable:!1,headerStyle:{textAlign:"center",backgroundColor:j.c.HEADER,color:j.c.TEXTHEADER},rowStyle:{backgroundColor:j.c.ROW,color:j.c.TEXTROW}},isLoading:F,localization:{toolbar:{searchTooltip:"T\xecm ki\u1ebfm",searchPlaceholder:"T\xecm ki\u1ebfm"},pagination:{labelDisplayedRows:"{from}-{to} c\u1ee7a {count}",labelRowsSelect:"h\xe0ng",labelRowsPerPage:"S\u1ed1 h\xe0ng m\u1ed7i trang:",firstAriaLabel:"Trang \u0111\u1ea7u",firstTooltip:"Trang \u0111\u1ea7u",previousAriaLabel:"Trang tr\u01b0\u1edbc",previousTooltip:"Trang tr\u01b0\u1edbc",nextAriaLabel:"Trang sau",nextTooltip:"Trang sau",lastAriaLabel:"Trang cu\u1ed1i",lastTooltip:"Trang cu\u1ed1i"},body:{emptyDataSourceMessage:"Kh\xf4ng c\xf3 b\u1ea3n ghi n\xe0o"}}})]}),O&&Object(z.jsx)(A,{open:O,handleClose:Z,item:H}),k&&Object(z.jsx)(E,{open:k,handleClose:Z,item:H}),M&&Object(z.jsx)(L.a,{title:"X\xe1c nh\u1eadn",text:"B\u1ea1n c\xf3 mu\u1ed1n x\xf3a k\u1ebf ho\u1ea1ch n\xe0y?",open:M,onConfirmDialogClose:Z,onYesClick:function(){var e;(e=H.id,u.a.delete(h+"/"+e)).then((function(e){200===e.data.statusCode?C.b.success("X\xf3a th\xe0nh c\xf4ng"):C.b.warning(e.data.message),Z()}))},Yes:"\u0110\u1ed3ng \xfd",No:"H\u1ee7y"})]})}},632:function(e,t,n){"use strict";var i=n(362);Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var a=i(n(364)),c=n(2),l=(0,a.default)((0,c.jsx)("path",{d:"M12 4.5C7 4.5 2.73 7.61 1 12c1.73 4.39 6 7.5 11 7.5s9.27-3.11 11-7.5c-1.73-4.39-6-7.5-11-7.5zM12 17c-2.76 0-5-2.24-5-5s2.24-5 5-5 5 2.24 5 5-2.24 5-5 5zm0-8c-1.66 0-3 1.34-3 3s1.34 3 3 3 3-1.34 3-3-1.34-3-3-3z"}),"RemoveRedEye");t.default=l},633:function(e,t,n){"use strict";var i=n(362);Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var a=i(n(364)),c=n(2),l=(0,a.default)((0,c.jsx)("path",{d:"M12 4V1L8 5l4 4V6c3.31 0 6 2.69 6 6 0 1.01-.25 1.97-.7 2.8l1.46 1.46C19.54 15.03 20 13.57 20 12c0-4.42-3.58-8-8-8zm0 14c-3.31 0-6-2.69-6-6 0-1.01.25-1.97.7-2.8L5.24 7.74C4.46 8.97 4 10.43 4 12c0 4.42 3.58 8 8 8v3l4-4-4-4v3z"}),"Loop");t.default=l},644:function(e,t,n){"use strict";var i=n(362);Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var a=i(n(364)),c=n(2),l=(0,a.default)((0,c.jsx)("path",{d:"M3 17.25V21h3.75L17.81 9.94l-3.75-3.75L3 17.25zM20.71 7.04c.39-.39.39-1.02 0-1.41l-2.34-2.34a.9959.9959 0 0 0-1.41 0l-1.83 1.83 3.75 3.75 1.83-1.83z"}),"Edit");t.default=l},645:function(e,t,n){"use strict";var i=n(362);Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var a=i(n(364)),c=n(2),l=(0,a.default)((0,c.jsx)("path",{d:"M6 19c0 1.1.9 2 2 2h8c1.1 0 2-.9 2-2V7H6v12zM19 4h-3.5l-1-1h-5l-1 1H5v2h14V4z"}),"Delete");t.default=l}}]);
//# sourceMappingURL=22.1e70932d.chunk.js.map