<template>
  <div>
    <div
      class="copy-code-wrap"
      @click="copy($event)"
      v-c-tooltip="'Copy to Clipboard'"
    >
      <div class="copy-code" id="copy-code"></div>
    </div>
    <codemirror :class="'editor'" v-model="code" :options="cmOptionCustom" />
    <!-- <codemirror :class="editor" v-model="code" :options="cmOptionCustom" /> -->
  </div>
</template>

<script>
import { codemirror } from "vue-codemirror";

// import language
import "codemirror/mode/javascript/javascript.js";
import "codemirror/mode/shell/shell.js";
// import base style
import "codemirror/lib/codemirror.css";
import "codemirror/theme/lesser-dark.css";

export default {
  components: {
    codemirror,
  },
  data() {
    return {
      cmOptions: {
        tabSize: 4,
        theme: "lesser-dark",
        line: true,
        readOnly: true,
      },
    };
  },
  props: {
    mode: { default: "text", type: String },
    code: { default: "", type: String },
    lineNumbers: { default: false, type: Boolean },
  },
  computed: {
    cmOptionCustom() {
      this.$set(this.cmOptions, "mode", this.mode);
      this.$set(this.cmOptions, "lineNumbers", this.lineNumbers);
      return this.cmOptions;
    },
  },
  methods: {
    copy(e) {
      if (e.which == 1) {
        let tempElem = document.createElement("textarea");
        tempElem.value = this.code;
        document.body.appendChild(tempElem);
        tempElem.select();
        document.execCommand("copy");
        document.body.removeChild(tempElem);

        // animate the button
        var copyIcon = document.getElementById("copy-code");
        let quickadd = () => {
          copyIcon.classList.add("animate");
          setTimeout(() => {
            copyIcon.classList.remove("animate");
          }, 200);
        };
        quickadd();
      }
    },
  },
};
</script>
<style scoped>
.copy-code-wrap {
  height: 34px;
  width: 34px;
  position: relative;
  top: 15px;
  right: -98%;
  z-index: 10;
  cursor: pointer;
}
.copy-code {
  height: 32px;
  width: 32px;
  position: absolute;
  bottom: 0;
  right: 0;
  background: #339af0;
  border-radius: 50%;
  z-index: 10;
  transition: 0.2s;
}
.copy-code::after {
  font-family: "Font Awesome 5 Free";
  font-weight: 600;
  font-size: 16px;
  content: "\f328";
  color: #fff;
  position: absolute;
  top: 3px;
  left: 10px;
}
.copy-code-wrap:active .copy-code {
  transform: translate(0, 0) scale(0.9);
}
.animate {
  transform: translate(0, 0) scale(1.12);
}

.tooltip {
  font-size: 15px;
  box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.5);
}
</style>
<style lang="scss">
.CodeMirror {
  height: auto !important;
}
</style>
