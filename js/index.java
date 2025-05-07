document.addEventListener("DOMContentLoaded", function () {
    const cards = document.querySelectorAll(".card");

    const observer = new IntersectionObserver((entries, observer) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add("show");
            } else {
                entry.target.classList.remove("show");
            }
        });
    }, { threshold: 0.2 }); // Ativa a animação quando 20% do card está visível

    cards.forEach(card => {
        observer.observe(card);
    });
});

let navBar = document.querySelector('#header')

document.addEventListener("scroll", ()=>{
  let scrollTop = window.scrollY;

  if(scrollTop > 0){
    navBar.classList.add('rolar');
  } else{
    navBar.classList.remove('rolar');
  }
})


let btnMenu = document.getElementById('btn-abrir-menu')
let menu = document.getElementById('menu-mobile')
let overlay = document.getElementById('overlay-menu')

btnMenu.addEventListener('click', ()=>{
  menu.classList.add('abrir-menu')
})


menu.addEventListener('click', ()=>{
  menu.classList.remove('abrir-menu')
})
overlay.addEventListener('click', ()=>{
  menu.classList.remove('abrir-menu')
})

window.addEventListener("load", () => {
  const container = document.querySelector(".carrossel");
  const imgsWrapper = document.getElementById("imgcarro");
  const imgList = document.querySelectorAll("#imgcarro img");

  // 💡 Duplicar as imagens para criar o loop infinito visual
  imgList.forEach((img) => {
    const clone = img.cloneNode(true);
    imgsWrapper.appendChild(clone);
  });

  const allImgs = document.querySelectorAll("#imgcarro img");
  const totalOriginal = imgList.length;

  let idx = 0;

  function carrossel() {
    const imgWidth = allImgs[0]?.clientWidth || 250;
    const marginRight = parseFloat(getComputedStyle(allImgs[0]).marginRight) || 0;

    const totalVisible = Math.max(1, Math.floor(container.clientWidth / (imgWidth + marginRight)));

    idx++;

    // ⚠️ Quando atingir a última imagem original (antes das duplicadas), reinicia
    if (idx >= totalOriginal + 1) {
      setTimeout(() => {
        imgsWrapper.style.transition = "none";
        imgsWrapper.style.transform = "translateX(0)";
        idx = 1;
      }, 500); // mesmo tempo da transição abaixo
    }

    const distance = (imgWidth + marginRight) * idx;

    imgsWrapper.style.transition = "transform 0.5s ease-in-out";
    imgsWrapper.style.transform = `translateX(-${distance}px)`;
  }

  setInterval(carrossel, 2500); // tempo entre as trocas
});
//carrossel video
window.addEventListener("load", () => {
  const videoContainer = document.getElementById("videoContainer");
  const videos = videoContainer.querySelectorAll("video");
  const setaDireita = document.getElementById("setaDireita");
  const setaEsquerda = document.getElementById("setaEsquerda");
  let currentIndex = 0;

  function playVideo(index) {
    videos.forEach((video, i) => {
      video.pause();
      video.currentTime = 0;
      video.muted = true;
    });

    const currentVideo = videos[index];
    currentVideo.muted = false;
    currentVideo.play();

    const offset = currentVideo.clientWidth * index + 8 * index;
    videoContainer.style.transform = `translateX(-${offset}px)`;
  }

  // Quando um vídeo termina, toca o próximo
  videos.forEach((video, index) => {
    video.addEventListener("ended", () => {
      currentIndex = (index + 1) % videos.length;
      playVideo(currentIndex);
    });

    // Se o usuário der play manual, para os outros
    video.addEventListener("play", () => {
      videos.forEach((v, i) => {
        if (i !== index) {
          v.pause();
          v.currentTime = 0;
          v.muted = true;
        }
      });
    });
  });

  // Setas
  setaDireita.addEventListener("click", () => {
    currentIndex = (currentIndex + 1) % videos.length;
    playVideo(currentIndex);
  });

  setaEsquerda.addEventListener("click", () => {
    currentIndex = (currentIndex - 1 + videos.length) % videos.length;
    playVideo(currentIndex);
  });

  // Iniciar com o primeiro vídeo
  playVideo(currentIndex);
});
/*aba comentarios*/
 // Efeito do header ao rolar
  window.addEventListener("scroll", function () {
    const header = document.querySelector("header");
    header.classList.toggle("rolar", window.scrollY > 50);
  });

  // Formulário de avaliações - enviar via Formspree
  const form = document.getElementById("formAvaliacao");
  if (form) {
    form.addEventListener("submit", async (e) => {
      e.preventDefault();

      const nome = document.getElementById("nome").value.trim();
      const avaliacao = document.getElementById("mensagem").value.trim();
      const estrelas = document.getElementById("estrelas").value;

      if (!nome || !avaliacao) {
        alert("Por favor, preencha todos os campos.");
        return;
      }

      const formData = new FormData();
      formData.append("Nome", nome);
      formData.append("Avaliação", avaliacao);
      formData.append("Estrelas", estrelas);

      try {
        const response = await fetch("https://formspree.io/f/xrbpoprn", {
          method: "POST",
          body: formData,
          headers: {
            Accept: "application/json"
          }
        });

        if (response.ok) {
          alert("Avaliação enviada com sucesso!");
          form.reset();
        } else {
          alert("Erro ao enviar. Tente novamente.");
        }
      } catch (error) {
        alert("Erro de conexão.");
        console.error(error);
      }
    });
  }

   const btn = document.getElementById("btnVerMais");
  const wrapper = document.getElementById("comentariosWrapper");

  btn.addEventListener("click", () => {
    wrapper.classList.toggle("expandido");
    if (wrapper.classList.contains("expandido")) {
      btn.textContent = "Ver menos avaliações";
    } else {
      btn.textContent = "Ver mais avaliações";
    }
  });

  // Se houver menos de 4 comentários, oculta o botão
  window.addEventListener("DOMContentLoaded", () => {
    const total = wrapper.querySelectorAll(".comentario").length;
    if (total <= 3) {
      btn.style.display = "none";
    }
  });












 function setCookie(nome, valor, horas) {
    const data = new Date();
    data.setTime(data.getTime() + (horas * 60 * 60 * 1000)); // Horas -> ms
    const expira = "expires=" + data.toUTCString();
    document.cookie = nome + "=" + valor + ";" + expira + ";path=/";
  }

  function getCookie(nome) {
    const cookies = document.cookie.split("; ");
    for (let i = 0; i < cookies.length; i++) {
      const c = cookies[i].split("=");
      if (c[0] === nome) {
        return c[1];
      }
    }
    return null;
  }

  function aceitarCookies() {
    setCookie("cookiesAceitos", "sim", 4); // Validade de 4 horas
    document.getElementById("cookie-banner").style.display = "none";
  }

  window.onload = function () {
    if (getCookie("cookiesAceitos") === "sim") {
      document.getElementById("cookie-banner").style.display = "none";
    }
  }


 








//certificados
const carrossel = document.getElementById("carrosselCertificados");
const btnEsq = document.getElementById("setaEsquerda");
const btnDir = document.getElementById("setaDireita");

// Calcular largura de um item visível
function getItemLargura() {
  const item = carrossel.querySelector(".certificado");
  const style = window.getComputedStyle(item);
  const margin = parseFloat(style.marginLeft) + parseFloat(style.marginRight);
  return item.offsetWidth + margin;
}

btnEsq.addEventListener("click", () => {
  carrossel.scrollBy({ left: -getItemLargura(), behavior: 'smooth' });
});

btnDir.addEventListener("click", () => {
  carrossel.scrollBy({ left: getItemLargura(), behavior: 'smooth' });
});
// Modal
const imagens = document.querySelectorAll(".certificado");
const modal = document.getElementById("modal");
const imagemModal = document.getElementById("imagemModal");
const fecharModal = document.getElementById("fecharModal");

imagens.forEach(img => {
  img.addEventListener("click", () => {
    modal.style.display = "block";
    imagemModal.src = img.src;
  });
});

fecharModal.addEventListener("click", () => {
  modal.style.display = "none";
});

// Fechar ao clicar fora da imagem
modal.addEventListener("click", (e) => {
  if (e.target === modal) {
    modal.style.display = "none";
  }
});


//carrossel video
window.addEventListener("load", () => {
    const container = document.getElementById("videoContainer");
    const videos = container.querySelectorAll("video");
    const setaDir = document.getElementById("setaDireita");
    const setaEsq = document.getElementById("setaEsquerda");

    let index = 0;

    const isMobile = () => window.innerWidth <= 600;

    function scrollToVideo(i) {
      const video = videos[i];
      const offset = video.offsetLeft - container.offsetLeft;
      container.style.transform = `translateX(-${offset}px)`;

      // pausa os outros vídeos
      videos.forEach((v, j) => {
        if (j !== i) {
          v.pause();
          v.currentTime = 0;
        }
      });

      // se quiser já dar play: video.play();
    }

    setaDir.addEventListener("click", () => {
      if (isMobile()) {
        index = Math.min(index + 1, videos.length - 1);
        scrollToVideo(index);
      } else {
        container.scrollBy({ left: 300, behavior: 'smooth' });
      }
    });

    setaEsq.addEventListener("click", () => {
      if (isMobile()) {
        index = Math.max(index - 1, 0);
        scrollToVideo(index);
      } else {
        container.scrollBy({ left: -300, behavior: 'smooth' });
      }
    });

    // pra garantir que o primeiro vídeo aparece no início no celular
    if (isMobile()) scrollToVideo(index);
  });















let scrollPositionImg = 0;

function scrollImages(direction) {
  const carousel = document.getElementById("imageCarousel");
  const itemWidth = carousel.querySelector(".image-wrapper").offsetWidth + 20; // 20 = gap
  scrollPositionImg += direction * itemWidth;

  const maxScroll = carousel.scrollWidth - carousel.clientWidth;
  scrollPositionImg = Math.max(0, Math.min(scrollPositionImg, maxScroll));

  carousel.style.transform = `translateX(-${scrollPositionImg}px)`;
}

function handleImageClick(src) {
  if (window.innerWidth <= 480) {
    // Modo mobile: mostra apenas a imagem clicada
    const viewer = document.getElementById("mobileViewer");
    const img = document.getElementById("mobileViewerImg");
    img.src = src;
    viewer.style.display = "flex";
  } else {
    // Modo desktop: zoom estilo lightbox (opcional — ou faz nada)
    window.open(src, "_blank");
  }
}

function closeMobileViewer() {
  const viewer = document.getElementById("mobileViewer");
  viewer.style.display = "none";
}












   let scrollPosition = 0;

  function scrollVideos(direction) {
    const carousel = document.getElementById("videoCarousel");
    const itemWidth = carousel.querySelector("iframe").offsetWidth + 20; // 20 = gap
    scrollPosition += direction * itemWidth;

    // Limita o scroll para não ir além
    const maxScroll = carousel.scrollWidth - carousel.clientWidth;
    scrollPosition = Math.max(0, Math.min(scrollPosition, maxScroll));

    carousel.style.transform = `translateX(-${scrollPosition}px)`;
  }


















document.querySelector('.mobile-viewer').style.display = 'flex';
document.querySelector('.mobile-viewer').style.display = 'none';