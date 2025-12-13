<template>
  <div id="app">
    <router-view />
  
    
  </div>
</template>

<style>
  #app {
    font-size: 14px;
    font-family: "PingFang SC", tahoma, arial, "helvetica neue",
    "hiragino sans gb", "microsoft yahei ui", "microsoft yahei", simsun,
    sans-serif;
    color: #2d2d2d;
    background: white;
    height: 100%;
  }
  
  ::-webkit-input-placeholder {
    color: rgb(117, 117, 117) !important;
  }
  
  ::-webkit-scrollbar {
    width: 17px !important;
  }
  html, body, #app {
    height: 100%;
  }

</style>
