document.addEventListener("DOMContentLoaded", () => {
    const algo = document.getElementById("algo");
    const container = document.querySelector('.container')

    if (algo){
        algo.onchange = () => {
            const value = algo.options[algo.selectedIndex];
            const xhr = new XMLHttpRequest();
            const url = `/OnedServlet?algo=${value}`;
            xhr.responseType = "json";

            xhr.addEventListener("load", () => {
                if(xhr.status === 200){
                    const reponse = xhr.response;
                    let content = "";

                    for (let i = 0; i < reponse.data.length(); i++) {
                        content +=
                            `<div className="bac">
                                <div className="objet">${i}</div>
                            </div>`;
                    }
                    container.innerHTML = content;
                }
            })

            xhr.open(`get`, url, true);
            xhr.send(null)
        }
    }
})