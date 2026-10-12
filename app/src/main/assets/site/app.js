const movies = [
 {id:1,title:"Action & adrénaline",year:"Sélection",category:"Action",symbol:"⚡",c1:"#b72d47",c2:"#24133e",description:"Découvre des films d’action et des aventures disponibles auprès de sources ouvertes. Cette fiche lance une recherche afin de trouver un contenu et de vérifier sa licence."},
 {id:2,title:"Aventures épiques",year:"Sélection",category:"Aventure",symbol:"⛰",c1:"#1c8c9b",c2:"#172447",description:"Voyages, exploration et aventures. Vérifie les conditions de diffusion indiquées par le site qui héberge chaque vidéo."},
 {id:3,title:"Animation",year:"Sélection",category:"Animation",symbol:"✦",c1:"#d66a35",c2:"#642a79",description:"Films d’animation et courts-métrages. Les œuvres et licences varient selon les résultats."},
 {id:4,title:"Monde documentaire",year:"Sélection",category:"Documentaire",symbol:"◉",c1:"#277d69",c2:"#172d42",description:"Documentaires et films éducatifs. Vérifie toujours les droits et la licence de chaque élément."},
 {id:5,title:"Les grands classiques",year:"Sélection",category:"Classique",symbol:"✧",c1:"#a77b3d",c2:"#372044",description:"Recherche des œuvres classiques. Le statut de domaine public dépend du pays et de l’œuvre exacte."},
 {id:6,title:"Courts-métrages",year:"Sélection",category:"Animation",symbol:"▶",c1:"#2f6eac",c2:"#25164b",description:"Courts-métrages indépendants, animation et créations proposées par leurs auteurs."},
 {id:7,title:"Nature & découverte",year:"Sélection",category:"Documentaire",symbol:"❋",c1:"#4d8b50",c2:"#183b50",description:"Vidéos de nature, sciences et découverte provenant de collections ouvertes."},
 {id:8,title:"Action classique",year:"Sélection",category:"Action",symbol:"✹",c1:"#a83e3e",c2:"#25264d",description:"Explore les collections de films et vérifie les autorisations avant de regarder ou partager."}
];
let category="Tous";
const grid=document.querySelector("#grid"), search=document.querySelector("#search"), count=document.querySelector("#count"), empty=document.querySelector("#empty");
const archiveSearch = m => "https://archive.org/search?query="+encodeURIComponent('mediatype:movies AND ('+m.category+' OR '+m.title+')');
function render(){
 const q=search.value.trim().toLowerCase();
 const list=movies.filter(m=>(category==="Tous"||m.category===category)&&[m.title,m.category,m.description].join(" ").toLowerCase().includes(q));
 grid.innerHTML=list.map(m=>`<article class="movie"><div class="poster" style="--c1:${m.c1};--c2:${m.c2}"><span class="poster-symbol">${m.symbol}</span><span class="poster-label">${m.title}</span></div><div class="movie-info"><h3>${m.title}</h3><div class="meta"><span>${m.year}</span><span>·</span><span class="tag">${m.category}</span></div><button data-id="${m.id}">Découvrir ↗</button></div></article>`).join("");
 count.textContent=list.length+" sélection"+(list.length>1?"s":"");
 empty.classList.toggle("hidden",list.length!==0);
 grid.querySelectorAll("button").forEach(b=>b.addEventListener("click",()=>showMovie(movies.find(m=>m.id===Number(b.dataset.id)))));
}
function showMovie(m){
 document.querySelector("#dialogPoster").style.background=`linear-gradient(135deg,${m.c1},${m.c2})`;
 document.querySelector("#dialogPoster").innerHTML=`<span>${m.symbol}</span>`;
 document.querySelector("#dialogTitle").textContent=m.title;
 document.querySelector("#dialogCategory").textContent=m.category.toUpperCase();
 document.querySelector("#dialogDescription").textContent=m.description;
 document.querySelector("#watchLink").href=archiveSearch(m);
 document.querySelector("#details").showModal();
}
search.addEventListener("input",render);
document.querySelector("#filters").addEventListener("click",e=>{const b=e.target.closest("button[data-category]");if(!b)return;category=b.dataset.category;document.querySelectorAll(".filter").forEach(x=>x.classList.toggle("active",x===b));render();});
document.querySelector("#closeDialog").addEventListener("click",()=>document.querySelector("#details").close());
document.querySelector("#details").addEventListener("click",e=>{if(e.target.id==="details")e.target.close()});
document.querySelector("#year").textContent=new Date().getFullYear();
let installPrompt;
window.addEventListener("beforeinstallprompt",e=>{e.preventDefault();installPrompt=e;document.querySelector("#installBtn").classList.remove("hidden")});
document.querySelector("#installBtn").addEventListener("click",async()=>{if(!installPrompt)return;installPrompt.prompt();await installPrompt.userChoice;installPrompt=null;document.querySelector("#installBtn").classList.add("hidden")});
if("serviceWorker" in navigator && location.protocol.startsWith("http")) window.addEventListener("load",()=>navigator.serviceWorker.register("./sw.js").catch(()=>{}));
render();
