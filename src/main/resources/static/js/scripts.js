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
    "password": document.querySelector('#signup_password').value
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
login_btn.addEventListener('click', (e) => {

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
  // bootstrap.Modal.getOrCreateInstance('#joinModal').hide();

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
      // console.log(JSON.parse(response));
    }
  });
}