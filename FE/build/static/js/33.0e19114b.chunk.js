(this["webpackJsonpmatx-react"]=this["webpackJsonpmatx-react"]||[]).push([[33],{1438:function(e,t,n){"use strict";n.r(t),n.d(t,"default",(function(){return P}));var c=n(13),a=n(1),i=n.n(a),r=n(218),s=n(97),l=n(640),o=n.n(l),d=n(118),u=n(175),j=u.a+"/api/recruits",h=u.a+"/api/planApprovalAdmin/approval-recruit",b=function(e){return d.a.put(h+"/"+e.id,e)},x=n(380),m=n(632),O=n.n(m),f=n(96),g=n(223),p=n(10),v=n(211),y=n(387),C=n(388),k=n(906),S=n(1045),T=n(219),w=n.n(T),A=n(598),N=n(591),R=n(676),z=n(2);function E(e){var t=e.open,n=e.handleClose,c=e.item,a=e.setItem,i=e.onSelectYes;return Object(z.jsxs)(y.a,{open:t,fullWidth:!0,maxWidth:"md",children:[Object(z.jsxs)(S.a,{style:{marginBlockEnd:0,padding:"16px 24px 0"},children:["Y\xeau c\u1ea7u b\u1ed5 sung",Object(z.jsx)(r.a,{className:"icon-close",onClick:n,children:Object(z.jsx)(x.a,{color:"error",children:Object(z.jsx)(w.a,{})})})]}),Object(z.jsxs)(R.ValidatorForm,{onSubmit:function(){return i(),void n()},onError:function(e){return console.log(e)},children:[Object(z.jsx)(k.a,{style:{padding:"20px"},children:Object(z.jsx)(R.TextValidator,{label:"N\u1ed9i dung y\xeau c\u1ea7u b\u1ed5 sung",variant:"outlined",fullWidth:!0,value:null===c||void 0===c?void 0:c.feedback,onChange:function(e){return a(Object(p.a)(Object(p.a)({},c),{},{feedback:e.target.value}))},validators:["required"],errorMessages:["Vui l\xf2ng nh\u1eadp tr\u01b0\u1eddng n\xe0y"]})}),Object(z.jsxs)(C.a,{children:[Object(z.jsx)(v.a,{variant:"contained",color:"primary",type:"submit",children:"L\u01b0u"}),Object(z.jsx)(v.a,{variant:"contained",color:"secondary",onClick:n,children:"\u0110\xf3ng"})]})]})]})}function W(e){var t=e.open,n=e.handleClose,s=e.item,l=e.setItem,o=i.a.useState(!1),d=Object(c.a)(o,2),j=d[0],h=d[1],m=i.a.useState(!1),O=Object(c.a)(m,2),T=O[0],R=O[1],W=i.a.useState(!1),Y=Object(c.a)(W,2),L=Y[0],P=Y[1],D=Object(a.useRef)(!1);Object(a.useEffect)((function(){D.current?4!==s.status&&6!==s.status&&10!==s.status||M():D.current=!0}),[s.status]);var M=function(){4===s.status?b(s).then((function(e){200===e.data.statusCode?(f.b.success("Ph\xea duy\u1ec7t k\u1ebf ho\u1ea1ch th\xe0nh c\xf4ng"),n()):f.b.warning(e.data.message)})).catch((function(e){return f.b.error("C\xf3 l\u1ed7i x\u1ea3y ra")})):10===s.status?b(s).then((function(e){200===e.data.statusCode?(f.b.success("Y\xeau c\u1ea7u b\u1ed5 sung k\u1ebf ho\u1ea1ch th\xe0nh c\xf4ng"),n()):f.b.warning(e.data.message)})).catch((function(e){return f.b.error("C\xf3 l\u1ed7i x\u1ea3y ra")})):6===s.status&&b(s).then((function(e){200===e.data.statusCode?(f.b.success("\u0110\xe3 t\u1eeb ch\u1ed1i k\u1ebf ho\u1ea1ch"),n()):f.b.warning(e.data.message)})).catch((function(e){return f.b.error("C\xf3 l\u1ed7i x\u1ea3y ra")}))};return Object(z.jsxs)(z.Fragment,{children:[Object(z.jsxs)(y.a,{open:t,fullWidth:!0,maxWidth:"md",children:[Object(z.jsxs)(S.a,{style:{marginBlockEnd:0,padding:"16px 24px 0"},children:["Th\xf4ng tin k\u1ebf ho\u1ea1ch",Object(z.jsx)(r.a,{className:"icon-close",onClick:n,children:Object(z.jsx)(x.a,{color:"error",children:Object(z.jsx)(w.a,{})})})]}),Object(z.jsx)(k.a,{style:{padding:"0 20px"},children:Object(z.jsxs)(A.a,{container:!0,spacing:2,children:[Object(z.jsx)(A.a,{container:!0,item:!0,xs:12,md:12,justifyContent:"center",children:Object(z.jsx)("h2",{children:null===s||void 0===s?void 0:s.titleRecruit})}),Object(z.jsxs)(A.a,{container:!0,item:!0,xs:4,md:4,children:[Object(z.jsx)(A.a,{item:!0,xs:4,md:4,className:"fw-600",children:"M\xe3 k\u1ebf ho\u1ea1ch:"}),Object(z.jsx)(A.a,{item:!0,xs:6,md:6,children:null===s||void 0===s?void 0:s.code})]}),Object(z.jsxs)(A.a,{container:!0,item:!0,xs:4,md:4,children:[Object(z.jsx)(A.a,{item:!0,xs:6,md:6,className:"fw-600",children:"S\u1ed1 l\u01b0\u1ee3ng tuy\u1ec3n d\u1ee5ng:"}),Object(z.jsx)(A.a,{item:!0,xs:5,md:5,children:null===s||void 0===s?void 0:s.quantity})]}),Object(z.jsxs)(A.a,{container:!0,item:!0,xs:4,md:4,children:[Object(z.jsx)(A.a,{item:!0,xs:6,md:6,className:"fw-600",children:"Tr\u1ea1ng th\xe1i k\u1ebf ho\u1ea1ch:"}),Object(z.jsx)(A.a,{item:!0,xs:5,md:5,children:Object(u.b)(null===s||void 0===s?void 0:s.status).message})]}),Object(z.jsxs)(A.a,{container:!0,item:!0,spacing:1,children:[Object(z.jsx)(A.a,{item:!0,xs:12,md:12,children:Object(z.jsx)("span",{className:"fz-18 fw-600",children:"M\xf4 t\u1ea3 c\xf4ng vi\u1ec7c"})}),Object(z.jsx)(A.a,{item:!0,xs:12,md:12,children:Object(z.jsx)(N.a,{fullWidth:!0,multiline:!0,variant:"standard",value:null===s||void 0===s?void 0:s.description,InputProps:{readOnly:!0,disableUnderline:!0}})})]}),Object(z.jsxs)(A.a,{container:!0,item:!0,spacing:1,children:[Object(z.jsx)(A.a,{item:!0,xs:12,md:12,children:Object(z.jsx)("span",{className:"fz-18 fw-600",children:"Y\xeau c\u1ea7u \u1ee9ng vi\xean"})}),Object(z.jsx)(A.a,{item:!0,xs:12,md:12,children:Object(z.jsx)(N.a,{fullWidth:!0,multiline:!0,variant:"standard",value:null===s||void 0===s?void 0:s.requireRecruit,InputProps:{readOnly:!0,disableUnderline:!0}})})]}),Object(z.jsxs)(A.a,{container:!0,item:!0,spacing:1,children:[Object(z.jsx)(A.a,{item:!0,xs:12,md:12,children:Object(z.jsx)("span",{className:"fz-18 fw-600",children:"Quy\u1ec1n l\u1ee3i"})}),Object(z.jsx)(A.a,{item:!0,xs:12,md:12,children:Object(z.jsx)(N.a,{fullWidth:!0,multiline:!0,variant:"standard",value:null===s||void 0===s?void 0:s.benefitsReceived,InputProps:{readOnly:!0,disableUnderline:!0}})})]}),Object(z.jsxs)(A.a,{container:!0,item:!0,spacing:1,children:[Object(z.jsx)(A.a,{item:!0,xs:12,md:12,children:Object(z.jsx)("span",{className:"fz-18 fw-600",children:"C\xe1c k\xeanh tuy\u1ec3n d\u1ee5ng"})}),Object(z.jsx)(A.a,{item:!0,xs:12,md:12,children:Object(z.jsx)(N.a,{fullWidth:!0,multiline:!0,variant:"standard",value:null===s||void 0===s?void 0:s.recruitmentChannel,InputProps:{readOnly:!0,disableUnderline:!0}})})]})]})}),Object(z.jsx)(C.a,{children:Object(z.jsxs)(z.Fragment,{children:[Object(z.jsx)(v.a,{variant:"contained",color:"primary",onClick:function(){return h(!0)},children:"Ph\xea duy\u1ec7t"}),Object(z.jsx)(v.a,{variant:"contained",color:"primary",onClick:function(){return P(!0)},children:"Y\xeau c\u1ea7u b\u1ed5 sung"}),Object(z.jsx)(v.a,{variant:"contained",color:"error",onClick:function(){return R(!0)},children:"T\u1eeb ch\u1ed1i"}),Object(z.jsx)(v.a,{variant:"contained",color:"secondary",onClick:n,children:"\u0110\xf3ng"})]})})]}),j&&Object(z.jsx)(g.a,{title:"X\xe1c nh\u1eadn",text:"B\u1ea1n c\xf3 mu\u1ed1n ph\xea duy\u1ec7t k\u1ebf ho\u1ea1ch n\xe0y?",open:j,onConfirmDialogClose:function(){return h(!1)},onYesClick:function(){l(Object(p.a)(Object(p.a)({},s),{},{status:4}))},Yes:"\u0110\u1ed3ng \xfd",No:"H\u1ee7y"}),T&&Object(z.jsx)(g.a,{title:"X\xe1c nh\u1eadn",text:"B\u1ea1n c\xf3 mu\u1ed1n t\u1eeb ch\u1ed1i ph\xea ho\u1ea1ch k\u1ebf ho\u1ea1ch n\xe0y?",open:T,onConfirmDialogClose:function(){return R(!1)},onYesClick:function(){l(Object(p.a)(Object(p.a)({},s),{},{status:6}))},Yes:"\u0110\u1ed3ng \xfd",No:"H\u1ee7y"}),L&&Object(z.jsx)(E,{open:L,handleClose:function(){return P(!1)},onSelectYes:function(){l(Object(p.a)(Object(p.a)({},s),{},{status:10}))},item:s,setItem:l})]})}var Y=n(633),L=n.n(Y);function P(){var e=Object(a.useState)([]),t=Object(c.a)(e,2),n=t[0],i=t[1],l=Object(a.useState)(!1),h=Object(c.a)(l,2),b=h[0],m=h[1],p=Object(a.useState)(!1),v=Object(c.a)(p,2),y=v[0],C=v[1],k=Object(a.useState)({}),S=Object(c.a)(k,2),T=S[0],w=S[1],A=Object(a.useState)(!1),N=Object(c.a)(A,2),R=N[0],E=N[1],Y=[{title:"STT",field:"STT",render:function(e){return e.tableData.id+1},cellStyle:{width:"3%",textAlign:"center"}},{title:"Thao t\xe1c",field:"action",render:function(e){return Object(z.jsx)(z.Fragment,{children:Object(z.jsx)(x.a,{color:"primary",onClick:function(){m(!0),w(e)},children:Object(z.jsx)(O.a,{})})})},cellStyle:{width:"5%",textAlign:"center"}},{title:"T\xean k\u1ebf ho\u1ea1ch",field:"name",render:function(e){return e.titleRecruit},cellStyle:{width:"10%",textAlign:"left"},headerStyle:{textAlign:"left"}},{title:"S\u1ed1 l\u01b0\u1ee3ng",field:"quantity",render:function(e){return e.quantity},cellStyle:{width:"5%",textAlign:"center"},headerStyle:{textAlign:"center"}},{title:"C\xe1c k\xeanh tuy\u1ec3n d\u1ee5ng ch\xednh",field:"description",render:function(e){return e.recruitmentChannel},headerStyle:{textAlign:"center"},cellStyle:{width:"20%",textOverflow:"ellipsis",textAlign:"center",whiteSpace:"nowrap",overflow:"hidden",maxWidth:100}},{title:"Tr\u1ea1ng th\xe1i",field:"status",render:function(e){var t=Object(u.b)(e.status).message,n=Object(u.b)(e.status).color;return Object(z.jsx)("div",{className:n,children:t})},cellStyle:{width:"10%",textAlign:"left"},headerStyle:{textAlign:"left"}}];Object(a.useEffect)((function(){E(!0),P()}),[]);var P=function(){d.a.get(j).then((function(e){200===e.data.statusCode?(E(!1),i(e.data.data.filter((function(e){return 2===e.status})))):(E(!1),f.b.warning("L\u1ed7i x\xe1c th\u1ef1c!"))})).catch((function(e){f.b.error("C\xf3 l\u1ed7i x\u1ea3y ra!"),E(!1)}))};console.log(n);var D=function(){C(!1),m(!1),w({}),P()};return Object(z.jsxs)(z.Fragment,{children:[Object(z.jsxs)(r.a,{style:{margin:20},children:[Object(z.jsx)(s.a,{routeSegments:[{name:"L\xe3nh \u0111\u1ea1o",path:"/leader"},{name:"Danh s\xe1ch k\u1ebf ho\u1ea1ch"}]}),Object(z.jsx)("div",{style:{display:"flex",justifyContent:"flex-end"},children:Object(z.jsx)(x.a,{color:"primary",onClick:function(){P()},children:Object(z.jsx)(L.a,{})})}),Object(z.jsx)("div",{children:Object(z.jsx)(o.a,{title:"Danh s\xe1ch k\u1ebf ho\u1ea1ch",columns:Y,data:n,options:{sorting:!1,maxBodyHeight:"60vh",draggable:!1,pageSize:10,pageSizeOptions:[10,20,50],headerStyle:{textAlign:"center",backgroundColor:u.c.HEADER,color:u.c.TEXTHEADER},rowStyle:{backgroundColor:u.c.ROW,color:u.c.TEXTROW}},isLoading:R,localization:{toolbar:{searchTooltip:"T\xecm ki\u1ebfm",searchPlaceholder:"T\xecm ki\u1ebfm"},pagination:{labelDisplayedRows:"{from}-{to} c\u1ee7a {count}",labelRowsSelect:"h\xe0ng",labelRowsPerPage:"S\u1ed1 h\xe0ng m\u1ed7i trang:",firstAriaLabel:"Trang \u0111\u1ea7u",firstTooltip:"Trang \u0111\u1ea7u",previousAriaLabel:"Trang tr\u01b0\u1edbc",previousTooltip:"Trang tr\u01b0\u1edbc",nextAriaLabel:"Trang sau",nextTooltip:"Trang sau",lastAriaLabel:"Trang cu\u1ed1i",lastTooltip:"Trang cu\u1ed1i"},body:{emptyDataSourceMessage:"Kh\xf4ng c\xf3 b\u1ea3n ghi n\xe0o"}}})})]}),b&&Object(z.jsx)(W,{open:b,handleClose:D,setItem:w,item:T}),y&&Object(z.jsx)(g.a,{title:"X\xe1c nh\u1eadn",text:"B\u1ea1n c\xf3 mu\u1ed1n x\xf3a k\u1ebf ho\u1ea1ch n\xe0y?",open:y,onConfirmDialogClose:D,onYesClick:function(){var e;(e=T.id,d.a.delete(j+"/"+e)).then((function(e){200===e.data.statusCode?f.b.success("X\xf3a th\xe0nh c\xf4ng"):f.b.warning(e.data.message),D()}))},Yes:"\u0110\u1ed3ng \xfd",No:"H\u1ee7y"})]})}},632:function(e,t,n){"use strict";var c=n(362);Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var a=c(n(364)),i=n(2),r=(0,a.default)((0,i.jsx)("path",{d:"M12 4.5C7 4.5 2.73 7.61 1 12c1.73 4.39 6 7.5 11 7.5s9.27-3.11 11-7.5c-1.73-4.39-6-7.5-11-7.5zM12 17c-2.76 0-5-2.24-5-5s2.24-5 5-5 5 2.24 5 5-2.24 5-5 5zm0-8c-1.66 0-3 1.34-3 3s1.34 3 3 3 3-1.34 3-3-1.34-3-3-3z"}),"RemoveRedEye");t.default=r},633:function(e,t,n){"use strict";var c=n(362);Object.defineProperty(t,"__esModule",{value:!0}),t.default=void 0;var a=c(n(364)),i=n(2),r=(0,a.default)((0,i.jsx)("path",{d:"M12 4V1L8 5l4 4V6c3.31 0 6 2.69 6 6 0 1.01-.25 1.97-.7 2.8l1.46 1.46C19.54 15.03 20 13.57 20 12c0-4.42-3.58-8-8-8zm0 14c-3.31 0-6-2.69-6-6 0-1.01.25-1.97.7-2.8L5.24 7.74C4.46 8.97 4 10.43 4 12c0 4.42 3.58 8 8 8v3l4-4-4-4v3z"}),"Loop");t.default=r}}]);
//# sourceMappingURL=33.0e19114b.chunk.js.map