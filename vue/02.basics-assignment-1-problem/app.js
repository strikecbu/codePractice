Vue.createApp({
  data() {
    return {
      name: "AndyChen",
      age: 35,
      imgUrl:
        "https://www.australia.com/content/australia/zh_tw/places/whitsundays-and-surrounds/guide-to-airlie-beach/jcr:content/mainParsys/imagewithcaption_cop_224039663/LargeImageTile/largeImageSrc.adapt.740.medium.jpg",
    };
  },
  methods: {
    getNum() {
      return Math.random() > 0.5 ? 1 : 0;
    },
  },
}).mount("#assignment");
