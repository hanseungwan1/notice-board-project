const postFormContainer = document.querySelector(".post_form_container");

const goDetail = (id) => {
    const btnContainer = document.querySelector(".btn_container");
    postFormContainer.action = "/news/update";

    btnContainer.innerHTML = `
            <input class="hidden" name=id value=${id} />
            <input class="input_btn" type="submit" value="글수정" />
            <input class="input_btn" type="button" value="글삭제" onclick="deletePost(${id})"/>
            <input class="input_btn" type="button" value="취소" onclick="cancelDetail()" />`;

    postFormContainer.classList.remove("display_none");
    postFormContainer.classList.add("display_flex");

    fetch(`/news/one?id=${id}`)
        .then((response) => response.json())
        .then((data) => {
            postFormContainer.children.namedItem("writer").value = data.writer;
            postFormContainer.children.namedItem("title").value = data.title;
            postFormContainer.children.namedItem("content").value = data.content;

            const countElement = document.getElementById(`${id}`);
            if (countElement) {
                countElement.textContent = data.cnt;
            }

        });

}

const displayPostPage = () => {
    postFormContainer.action = "/news/insert";
    const btnContainer = document.querySelector(".btn_container");
    clearData();
    btnContainer.innerHTML =
        `<input class="input_btn" type="submit" value="등록"/>
        <input class="input_btn" type="button" value="재작성" onclick="clearData()"/>
        <input class="input_btn" type="button" value="취소" onclick="cancelDetail()" />`;

    postFormContainer.classList.remove("display_none");
    postFormContainer.classList.add("display_flex");
}

const cancelDetail = () => {
    postFormContainer.classList.remove("display_flex");
    postFormContainer.classList.add("display_none");
}

const deletePost = (id) => {
    location.href = `/news/delete?id=${id}`;
}

const clearData = () =>{

    postFormContainer.children.namedItem("writer").value = "";
    postFormContainer.children.namedItem("title").value = "";
    postFormContainer.children.namedItem("content").value = "";
}

const goHome = () =>{
    location.href = `/newsmain`;
}