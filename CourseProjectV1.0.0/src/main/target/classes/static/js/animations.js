document.addEventListener("DOMContentLoaded", () => {

    // Плавное появление элементов
    document.querySelectorAll(".fade-in").forEach((el, index) => {
        el.style.animationDelay = `${index * 0.1}s`;
    });

    // Подтверждение удаления
    document.querySelectorAll("[data-confirm]").forEach(button => {
        button.addEventListener("click", (e) => {
            if (!confirm("Вы уверены, что хотите выполнить это действие?")) {
                e.preventDefault();
            }
        });
    });

});