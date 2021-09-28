export function check(form) {
  let result = true;
  form.querySelectorAll("[required]").forEach((o) => {
    if (!o.checkValidity()) {
      form.classList.add("was-validated");
      result = false;
      return;
    }
  });
  return result;
}
