const silder = document.querySelector('#slider');
const wrapper = document.querySelector('.wrapper');
const items = document.querySelector('.items');
const item = document.querySelectorAll('.slide_item');
const next = document.querySelector('.next');
const prev = document.querySelector('.prev');
const signup_btn = document.querySelector('#signup_button');
const login_btn = document.querySelector('#login_btn');
const fileInput = document.querySelector('#file-input');
const preview = document.querySelector('#preview');
const recent =  document.querySelector('#recent_btn');
const most =  document.querySelector('#most_btn');
const post_btns = document.querySelectorAll('.post-btns');
const plus_btn = document.querySelector('.plusbtn');
const card_posts = document.querySelectorAll('.card-post');
const post_detail_inner = document.querySelector('.post-ditail-inner');
const confirm_auction = document.querySelector('#confirm_auction');
const mypage_btns = document.querySelectorAll('.mypage-btns');
const immediate = document.querySelector("#immediate");
const detail_btn = document.querySelectorAll('.detail-btn');

const itemCount = item.length - 2;
let startX = 0;         //mousedown시 위치
let moveX = 0;         //움직인 정도
let currentIdx = 2;    //현재 위치(index)
let positions = [];

function initializeData() {
  console.log();
  const isActive = items.classList.contains('active');
  if (isActive) items.classList.remove('active');
  const width = wrapper.clientWidth;
  const interval = item[1].clientWidth;
  const margin = (width - interval) / 2
  const initX = Math.floor((interval - margin) * -1);
  let pos = [];
  for (let i=0; i<itemCount; i++) {
    pos.push(initX - interval * i);
  }
  positions = pos;
  items.style.width = (itemCount + 1)*100 + '%';
  items.style.left = positions[currentIdx] + 'px';
  silder.style.visibility = 'visible';
}
if(item.length > 0){
  initializeData();
  window.addEventListener('resize', initializeData);
  window.addEventListener('load', initializeData);
  // btn click event

  next.addEventListener('click', (e) => {
    if (currentIdx === itemCount - 1) return;
    const isActive = items.classList.contains('active');
    if (!isActive) items.classList.add('active');
    currentIdx = currentIdx + 1;
    items.style.left = positions[currentIdx] + 'px';
  });
  prev.addEventListener('click', (e) => {
    if (currentIdx === 0) return;
    const isActive = items.classList.contains('active');
    if (!isActive) items.classList.add('active');
    currentIdx = currentIdx - 1;
    items.style.left = positions[currentIdx] + 'px';
  });


  wrapper.onmousedown =(e)=> {
    const rect = wrapper.getBoundingClientRect();
    startX = e.clientX - rect.left;
    const isActive = items.classList.contains('active');
    if (!isActive) items.classList.add('active');
    items.addEventListener('mousemove', onMouseMove);
    document.onmouseup =(e)=> {
      if (wrapper.classList.contains('active')) wrapper.classList.remove('active');
      items.removeEventListener('mousemove', onMouseMove);
      document.onmouseup = null;
      if (moveX > -70 && moveX <= 70) {
        //   만약 -70~70이면 초기위치로 이동
        return items.style.left = positions[currentIdx] + 'px';
      }
      if (moveX > 0 && currentIdx > 0) {
        currentIdx = currentIdx - 1;
        items.style.left = positions[currentIdx] + 'px';
      }
      if (moveX < 0 && currentIdx < itemCount - 1){
        currentIdx = currentIdx + 1;
        items.style.left = positions[currentIdx] + 'px';
      }
    }
  }
}

function onMouseMove(e) {
    if (!wrapper.classList.contains('active')) wrapper.classList.add('active');
    const rect = wrapper.getBoundingClientRect();
    moveX = e.clientX - rect.left - startX;
    const left = positions[currentIdx] + moveX;
    if (currentIdx === 0 && moveX > 0) return;
    else if(currentIdx === itemCount - 1 && moveX < 0) return;
    items.style.left = left + 'px';
  }



const self_price = document.querySelector('#self-price');
if(self_price != null){
  self_price.addEventListener('click', (e) => {
  document.querySelector('#option7').checked = true;
  });
}

var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
  return new bootstrap.Tooltip(tooltipTriggerEl)
})
var popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'))
var popoverList = popoverTriggerList.map(function (popoverTriggerEl) {
  return new bootstrap.Popover(popoverTriggerEl)
})

function sendAjaxRequest(url, method, data, callback) {
  var xhr = new XMLHttpRequest();
  xhr.open(method, url, true);
  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4) { // 요청이 완료되었을 때
      if (xhr.status === 200 || xhr.status === 201)  { // 응답 상태 코드가 200 (성공)인 경우
        callback(null, xhr.responseText); // 콜백 함수 호출 (오류 없음)
      } else {
        callback(new Error('Request failed with status: ' + xhr.status), null); // 콜백 함수 호출 (오류 발생)
      }
    }
  };
  xhr.setRequestHeader('Content-Type', 'application/json');
  xhr.send(JSON.stringify(data));
}

//
// var requestData = {
//   "email": "abc1234@gmail.com",
//   "name": "김태경",
//   "password": "gkrwls12"
// };
// sendAjaxRequest('/auth/signup', 'POST', requestData, function (error, response) {
//   if (error) {
//     console.error('AJAX request error:', error);
//   } else {
//     console.log('AJAX response:', response);
//   }
// });

signup_btn.addEventListener('click', (e) => {

  var requestData = {
    "email": document.querySelector('#signup_email').value,
    "name": document.querySelector('#signup_name').value,
    "password": document.querySelector('#signup_password').value,
    "password2": document.querySelector('#signup_password2').value,
    "phoneNumber" : document.querySelector('#signup_phone1').value +
        document.querySelector('#signup_phone2').value +
        document.querySelector('#signup_phone3').value,
    "account" : "user",
    "nickname": document.querySelector('#signup_nickname').value
  };
  sendAjaxRequest('/auth/signup', 'POST', requestData, function (error, response) {
    if (error) {
      console.error('AJAX request error:', error);
    } else {
      console.log('AJAX response:', response);
    }
  });
  // bootstrap.Modal.getOrCreateInstance('#joinModal').hide();

});
var a = "";
function login(){
  var requestData = {
    "email": document.querySelector('#login_id').value,
    "password": document.querySelector('#login_password').value,
  };
  sendAjaxRequest('/auth/login', 'POST', requestData, function (error, response) {
    if (error) {
      // console.error('AJAX request error:', error);
      alert("잘못된 로그인 정보입니다. 다시시도해주세요.");
    } else {
      // console.log('AJAX response:', JSON.stringify(response));
      console.log(JSON.parse(response).accessToken);
      document.cookie = "jwtToken="+JSON.parse(response).accessToken;
      location.reload();
    }
  });
}
login_btn.addEventListener('click', (e) => {
  login();
});

imageLoader = function(file){
  var reader = new FileReader();
  reader.onload = function(ee){
    // let img = document.createElement('img')
    // img.src = ee.target.result;
    preview.innerHTML += `
                        <div id="${file.lastModified}" class="empty-img-box my-1 mx-1 col-6">
                            <img src="${ee.target.result}">
                            <button data-index='${file.lastModified}' class='file-remove'></button>
                        </div>`;


  }
  reader.readAsDataURL(file);
}

const dataTranster = new DataTransfer();

const handler = {
  init() {
    if(fileInput != null){
      fileInput.addEventListener('change', () => {
        console.dir(fileInput)
        const files = Array.from(fileInput.files);
        Array.from(files).forEach(file => {
          dataTranster.items.add(file);
        });
        document.querySelector('#file-input').files = dataTranster.files;

        files.forEach(file => {
          imageLoader(file);
        });
      });
    }

  },

  removeFile: () => {
    document.addEventListener('click', (e) => {
      if(e.target.className !== 'file-remove') return;
      const removeTargetId = e.target.dataset.index;
      const removeTarget = document.getElementById(removeTargetId);
      const files = document.querySelector('#file-input').files;

      // document.querySelector('#file-input').files =
      //             Array.from(files).filter(file => file.lastModified !== removeTarget);

      Array.from(files)
          .filter(file => file.lastModified != removeTargetId)
          .forEach(file => {
            dataTranster.items.add(file);
          });

      document.querySelector('#file-input').files = dataTranster.files;

      removeTarget.remove();
    })
  }
}

handler.init()
handler.removeFile()


let orderPageNum = 0;
let orderPageSize = 12;
let orderText = "sort=createDate,desc";

function load_recent(order, new_flag,size,page){
  var requestData = {};
  console.log('/post/readAll2?'+order+"&size="+size+"&page"+page);
  sendAjaxRequest('/post/readAll2?'+order+"&size="+size+"&page="+page, 'GET', requestData, function (error, response) {
    if (error) {
      console.error('AJAX request error:', error);
      // alert("잘못된 로그인 정보입니다. 다시시도해주세요.");
    } else {
      if(new_flag == true){
        document.querySelector('.card-list').innerHTML = response;
      }else{
        document.querySelector('.card-list').innerHTML += response;
      }
    }
  });
}

if(document.querySelector('.card-list') != null) {
  load_recent(orderText, true, orderPageSize, orderPageNum);
}
post_btns.forEach(function(post_btn) {
  post_btn.addEventListener('change', function() {
      if (this.checked) {
        var selectedId = this.id;
          orderPageNum = 0;
        if (selectedId === 'recent_btn') {
          orderText = "sort=createDate,desc";
          load_recent(orderText,true,orderPageSize,orderPageNum);
        } else if (selectedId === 'most_btn') {
          orderText = "sort=viewCount,desc"
          load_recent(orderText,true,orderPageSize,orderPageNum);
        }
      }
    });
});



if(plus_btn != null){
  plus_btn.addEventListener('click', function() {
    orderPageNum += 1;
    load_recent(orderText, false, orderPageSize, orderPageNum);
  });
}

var modal = document.querySelector('#postDetailModal');
modal.addEventListener('hidden.bs.modal', function () {
  // 모달이 숨겨질 때 실행할 코드 작성
  document.querySelector('#post_detail_author').innerText = "";
  document.querySelector('#post_detail_title').innerText = "";
  document.querySelector('#post_detail_content').innerText = "";
  document.querySelector('#post_detail_biddingPrice').innerText = "";
  document.querySelector('#post_detail_immediatePurchasePrice').innerText = "";
});



function post_detail_btn(element) {
  var dataIdValue = element.dataset.id;

  var requestData = {};
  sendAjaxRequest('/post/'+dataIdValue, 'GET', requestData, function (error, response) {
    if (error) {
      // console.error('AJAX request error:', error);
    } else {
      var json = JSON.parse(response);
      document.querySelector('#post_detail_author').innerText = json.user.name;
      document.querySelector('#post_detail_title').innerText = json.title;
      document.querySelector('#post_detail_content').innerText = json.content;
      document.querySelector('#post_detail_biddingPrice').innerText = json.biddingPrice;
      document.querySelector('#post_detail_immediatePurchasePrice').innerText = json.immediatePurchasePrice;
      document.querySelector('#post_id').value = json.id;

      console.log(json);

      const serverDate = new Date(json.targetDate);
      const currentDate = new Date();
      const timeDifference = serverDate - currentDate;
      const daysDifference = Math.floor(timeDifference / (1000 * 60 * 60 * 24));
      const hoursDifference = Math.floor((timeDifference % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
      const minutesDifference = Math.floor((timeDifference % (1000 * 60 * 60)) / (1000 * 60));

      document.querySelector('#remain_time').innerHTML = daysDifference + "일 "+ hoursDifference + "시간 남음";
      // console.log(daysDifference + "일 "+ hoursDifference + "시간 남음");

      const formatter = new Intl.DateTimeFormat('ko-KR', {
        year: '2-digit',
        month: 'numeric',
        day: 'numeric',
        hour: 'numeric',
        minute: 'numeric',
      });

      const formattedDateString = formatter.format(serverDate);
      document.querySelector('#target_date').innerHTML = `(${formattedDateString} 에 종료)`;

      console.log(`(${formattedDateString} 에 종료)`);

      post_detail_inner.innerHTML = "";
      for(var i = 0; i< json.fileArray.length; i++){
        if(i==0){
          post_detail_inner.innerHTML += `
            <div class="carousel-item active">
                <img src="/upload/${json.fileArray[i]}" class="d-block w-100" alt="...">
            </div>`;
        }else{
          post_detail_inner.innerHTML += `
            <div class="carousel-item">
                <img src="/upload/${json.fileArray[i]}" class="d-block w-100" alt="...">
            </div>`;
        }

      }

    }
  });
  sendAjaxRequest('/auction/readByPost/'+dataIdValue, 'GET', requestData, function (error, response) {
    if (error) {
      // console.error('AJAX request error:', error);
    } else {
      document.querySelector('#post_detail_auction').innerHTML = response;


    }
  });
}

confirm_auction.addEventListener('click', (e) => {
  let post_id = document.querySelector('#post_id').value
  var requestData = {
    "postid": post_id
  };
  sendAjaxRequest('/auction/create/'+ post_id, 'POST', requestData, function (error, response) {
    if (error) {
      // console.error('AJAX request error:', error);
      // alert("잘못된 로그인 정보입니다. 다시시도해주세요.");
      // alert(error);
      if(document.querySelector(".user-name") == null){
        alert("로그인 후 이용바랍니다.");
      }else{
        alert(error);
      }
    } else {
      alert("입찰되었습니다.")
    }
  });
});


function my_page(url){
  var requestData = {};
  sendAjaxRequest(url, 'GET', requestData, function (error, response) {
    if (error) {
      console.error('AJAX request error:', error);
      // alert("잘못된 로그인 정보입니다. 다시시도해주세요.");
    } else {
      document.querySelector('.mypage_content').innerHTML = response;
    }
  });
}

if(document.querySelector('.mypage_content') != null) {
  my_page("/mypage1");
}
mypage_btns.forEach(function(mypage_btn) {
  mypage_btn.addEventListener('change', function() {
    if (this.checked) {
      var selectedId = this.id;
      orderPageNum = 0;
      console.log("Ddd");
      if (selectedId === 'option1') {
        my_page("/mypage1");
      } else if (selectedId === 'option2') {
        console.log("Ddd");
        my_page("/mypage2");
      } else if (selectedId === 'option3') {
        my_page("/mypage3");
      } else if (selectedId === 'option4') {
        my_page("/mypage4");
      } else if (selectedId === 'option5') {
        my_page("/mypage5");
      }
    }
  });
});


immediate.addEventListener('click', (e) => {
  let post_id = document.querySelector('#post_id').value
  var requestData = {
    "postid": post_id
  };
  sendAjaxRequest('/auction/immediate/'+ post_id, 'POST', requestData, function (error, response) {
    if (error) {
      // alert(error);
      alert("로그인후 이용바랍니다. ")
    } else {
      alert("즉시구매가 완료되었습니다.")
      location.reload();
    }
  });
});

detail_btn.forEach(function(detail) {
  detail.addEventListener('change', function() {
    if (this.checked) {
      var selectedId = this.id;
      orderPageNum = 0;
      if (selectedId === 'detail') {
        document.querySelector("#post_detail_content").style.display = "block";
        document.querySelector("#post_detail_auction").style.display = "none";
      } else if (selectedId === 'history') {
        document.querySelector("#post_detail_content").style.display = "none";
        document.querySelector("#post_detail_auction").style.display = "block";
      }
    }
  });
});
function kakaopay_btn(){
  window.open("kakaopay?price="+getSelectedValue(), "a", "width=400, height=800, left=100, top=50");
}
function getSelectedValue() {
  var radios = document.getElementsByName('charge_price');

  for (var i = 0; i < radios.length; i++) {
    if (radios[i].checked) {
      if(radios[i].value=="self"){
        if(document.querySelector('#self-price').value != ""){
          return document.querySelector('#self-price').value;
        }
      }
      return radios[i].value;
    }
  }

  return null; // 선택된 값이 없을 경우 null 반환
}


if(document.getElementsByName('charge_price') != null) {
  if(document.querySelector("#user-money") != null) {
    document.querySelector("#user-plus-money").innerText = parseInt(getSelectedValue()) + parseInt(document.querySelector("#user-money").innerText);
  }
}


if(document.getElementsByName('charge_price') != null){
  document.getElementsByName('charge_price').forEach(function(charge_text) {
    charge_text.addEventListener('change', function() {
      if (this.checked) {
        document.querySelector("#user-plus-money").innerText = parseInt(getSelectedValue()) + parseInt(document.querySelector("#user-money").innerText);
      }
    });
  });
}

