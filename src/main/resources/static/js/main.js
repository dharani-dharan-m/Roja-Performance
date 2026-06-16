const menuGroups = {
    models: {
        title: "Models / Builds",
        links: ["Supercars", "SUVs", "Sedans", "Off-road Builds", "Track Builds", "Daily Performance Builds"]
    },
    services: {
        title: "Services",
        links: ["Stage 1 ECU Remap", "Stage 2 Performance Tune", "Stage 3 Turbo Upgrade", "Engine Swaps", "Exhaust Systems", "Suspension Setup", "Brake Upgrades", "Dyno Testing"]
    },
    wheels: {
        title: "Wheels & Tyres",
        links: ["Performance Tyres", "Alloy Wheels", "Forged Rims", "Track Tyres", "Off-road Tyres", "Wheel Alignment", "Tyre Fitment Guide"]
    },
    customization: {
        title: "Customization",
        links: ["Vinyl Wraps", "Paint Protection Film", "Full Body Paint", "Widebody Kits", "Carbon Fibre Parts", "Interior Upholstery", "Ambient Lighting", "Custom Steering Wheels"]
    },
    company: {
        title: "Company",
        links: ["About Roja Performance", "Our Workshop", "Projects", "News", "Careers", "Contact"]
    }
};

const loader = document.getElementById("loader");
const loaderBar = document.getElementById("loaderBar");
const loaderPercent = document.getElementById("loaderPercent");
const header = document.getElementById("siteHeader");
const menuButton = document.getElementById("menuButton");
const closeMenu = document.getElementById("closeMenu");
const menuOverlay = document.getElementById("menuOverlay");
const menuGrid = document.getElementById("menuGrid");
let menuMode = "all";

function simulateLoader() {
    let progress = 0;
    const timer = window.setInterval(() => {
        progress = Math.min(100, progress + Math.ceil(Math.random() * 14));
        loaderBar.style.width = `${progress}%`;
        loaderPercent.textContent = `${progress}%`;
        if (progress >= 100) {
            window.clearInterval(timer);
            window.setTimeout(() => loader.classList.add("hidden"), 360);
        }
    }, 95);
}

function setHeaderState() {
    header.classList.toggle("scrolled", window.scrollY > 28);
}

function renderMenu(featureKey = null) {
    const entries = Object.entries(menuGroups);
    const sorted = featureKey
        ? entries.sort(([key]) => key === featureKey ? -1 : 1)
        : entries;

    menuGrid.innerHTML = sorted.map(([key, group]) => `
        <section class="menu-column ${featureKey === key ? "featured" : ""}">
            <h3>${group.title}</h3>
            ${group.links.map(link => `<a href="#${key === "wheels" ? "wheels" : key === "company" ? "contact" : key}">${link}</a>`).join("")}
        </section>
    `).join("");
}

function openMenu(featureKey = null) {
    renderMenu(featureKey);
    menuMode = featureKey || "all";
    menuOverlay.classList.add("open");
    menuOverlay.setAttribute("aria-hidden", "false");
    menuButton.classList.add("active");
    menuButton.setAttribute("aria-expanded", "true");
    header.classList.add("menu-active");
    document.body.classList.add("no-scroll");
}

function closeMenuOverlay() {
    menuOverlay.classList.remove("open");
    menuOverlay.setAttribute("aria-hidden", "true");
    menuButton.classList.remove("active");
    menuButton.setAttribute("aria-expanded", "false");
    header.classList.remove("menu-active");
    document.body.classList.remove("no-scroll");
}

menuButton.addEventListener("click", () => {
    if (menuOverlay.classList.contains("open") && menuMode === "all") {
        closeMenuOverlay();
    } else {
        openMenu();
    }
});

closeMenu.addEventListener("click", closeMenuOverlay);

[...document.querySelectorAll("[data-menu]")].forEach(link => {
    const key = link.dataset.menu;
    link.addEventListener("mouseenter", () => {
        if (!window.matchMedia("(max-width: 1180px)").matches) openMenu(key);
    });
    link.addEventListener("click", event => {
        if (window.matchMedia("(max-width: 1180px)").matches) return;
        event.preventDefault();
        openMenu(key);
    });
});

menuOverlay.addEventListener("click", event => {
    const link = event.target.closest("a[href^='#']");
    if (link) closeMenuOverlay();
});

document.querySelectorAll("a[href^='#']").forEach(anchor => {
    anchor.addEventListener("click", event => {
        const targetId = anchor.getAttribute("href");
        const target = document.querySelector(targetId);
        if (!target) return;
        event.preventDefault();
        closeMenuOverlay();
        const offset = header.offsetHeight;
        const y = target.getBoundingClientRect().top + window.scrollY - offset + 1;
        window.scrollTo({ top: y, behavior: "smooth" });
    });
});

const revealObserver = new IntersectionObserver(entries => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            entry.target.classList.add("visible");
            revealObserver.unobserve(entry.target);
        }
    });
}, { threshold: 0.16 });

document.querySelectorAll(".reveal").forEach((element, index) => {
    element.style.transitionDelay = `${Math.min(index * 35, 210)}ms`;
    revealObserver.observe(element);
});

document.querySelectorAll(".tilt-card").forEach(card => {
    card.addEventListener("mousemove", event => {
        const rect = card.getBoundingClientRect();
        const x = ((event.clientX - rect.left) / rect.width - 0.5) * 8;
        const y = ((event.clientY - rect.top) / rect.height - 0.5) * -8;
        card.style.transform = `perspective(900px) rotateY(${x}deg) rotateX(${y}deg) translateY(-4px)`;
    });
    card.addEventListener("mouseleave", () => {
        card.style.transform = "";
    });
});

const configForm = document.getElementById("configForm");
const configSummary = document.getElementById("configSummary");

configForm.addEventListener("submit", event => {
    event.preventDefault();
    const vehicleType = document.getElementById("vehicleType").value;
    const servicePackage = document.getElementById("servicePackage").value;
    const finish = document.getElementById("finish").value;
    configSummary.innerHTML = `<strong>Quote brief created:</strong> ${vehicleType} with ${servicePackage} package in ${finish}. Our consultant will refine fitment, tuning limits, and budget.`;
});

const contactForm = document.getElementById("contactForm");
const contactMessage = document.getElementById("contactMessage");

contactForm.addEventListener("submit", event => {
    event.preventDefault();
    const name = document.getElementById("name").value.trim();
    contactMessage.textContent = `Thank you${name ? `, ${name}` : ""}. Your enquiry has been captured. Roja Performance will contact you soon.`;
    contactForm.reset();
    window.setTimeout(() => {
        contactMessage.textContent = "";
    }, 6500);
});

window.addEventListener("scroll", setHeaderState, { passive: true });
window.addEventListener("keydown", event => {
    if (event.key === "Escape") closeMenuOverlay();
});
window.addEventListener("load", simulateLoader);

renderMenu();
setHeaderState();
window.setTimeout(() => {
    if (!loader.classList.contains("hidden")) loader.classList.add("hidden");
}, 2500);