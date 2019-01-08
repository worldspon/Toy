$('.quantity_plus').click(function(){
    let index=$(".quantity_plus").index(this);
    let menu_quantity = document.getElementsByClassName("quantity_box");
    let menu_price = document.getElementsByClassName('menu_price');
    if(menu_quantity[index].innerHTML!=5){
        menu_quantity[index].innerHTML++;
        menu_price[index].innerHTML =5900*menu_quantity[index].innerHTML;
    } else {
        return 0;
    }
});

$('.quantity_minus').click(function(){
    let index=$(".quantity_minus").index(this);
    let menu_quantity = document.getElementsByClassName("quantity_box");
    let menu_price = document.getElementsByClassName('menu_price');
    if(menu_quantity[index].innerHTML!=1){
        menu_quantity[index].innerHTML--;
        menu_price[index].innerHTML =5900*menu_quantity[index].innerHTML;
    } else {
        return 0;
    }
});

$('.add_cart').click(function(){
    console.log("A");
});

function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+ d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
  };

  function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i <ca.length; i++) {
      var c = ca[i];
      while (c.charAt(0) == ' ') {
        c = c.substring(1);
      }
      if (c.indexOf(name) == 0) {
        return c.substring(name.length, c.length);
      }
    }
    return "";
  };