function crearTrago(event) {
    event.preventDefault();

    const name = document.getElementById("name").value;
    const instructions = document.getElementById("instructions").value;
    const imageUrl = document.getElementById("imageUrl").value;

    const data = {
        name,
        instructions,
        imageUrl
    }

    fetch("/api/drinks", {
        method: "POST",
        body: JSON.stringify(data),
        headers: {
            "Content-Type": "application/json",
        }
    })
        .then(res => {
            if (res.status === 201) {
                window.location.href = '/pages/tragos.html';
            }
        })
        .catch(err => {
            console.error("err", err);
            const sectioncrear = document.querySelector(".crear-trago");
            sectioncrear.innerHTML = "No se pudo crear el trago"
        })
}