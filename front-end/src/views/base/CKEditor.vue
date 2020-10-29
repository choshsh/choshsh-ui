<template>
  <ckeditor
    :editor="editor"
    :config="editorConfig"
    :disabled="disable"
    v-model="editorData"
  ></ckeditor>
</template>

<style>
.ck-editor__editable {
  min-height: 250px;
}
</style>
<script>
import CKEditor from "@ckeditor/ckeditor5-vue";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";

export default {
  components: {
    ckeditor: CKEditor.component,
  },
  data() {
    return {
      editor: ClassicEditor,
      editorData: "",
      editorConfig: {
        toolbar: [
          "heading",
          "|",
          "bold",
          "italic",
          "link",
          "bulletedList",
          "numberedList",
          "blockQuote",
          "|",
          "indent",
          "outdent",
          "|",
          "insertTable",
          "tableColumn",
          "tableRow",
          "mergeTableCells",
          "|",
          "undo",
          "redo",
        ],
        link: {
          addTargetToExternalLinks: true,
        },
      },
    };
  },
  props: {
    disable: {
      type: Boolean,
      default() {
        return false;
      },
    },
    dataParam: String,
    hideToolbar: {
      type: Boolean,
      default() {
        return false;
      },
    },
  },
  methods: {
    getData() {
      return this.editorData;
    },
  },
  created: function () {
    this.editorData = this.dataParam;
    if (this.hideToolbar) this.editorConfig["toolbar"] = [""];
  },
};
</script>