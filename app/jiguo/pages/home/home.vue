<template>
	<view>
		<view class="status_bar"></view>
		<view class="uni-margin-wrap">
			<swiper class="swiper" indicator-dots="true" autoplay="true" duration="400" interval="2000" circular="true">
				<swiper-item v-for="(img,index) in carousel" :key='index'>
					<view class="swiper-item uni-bg-red">
						<image :src="buildImageUrl(img)" :key="img"></image>
					</view>
				</swiper-item>
			</swiper>
		</view>
		<view class="search-bar">
			<view class="iconfont icon-sousuo"></view>
			<view class="">
				<input @focus="gotoSearch" type="text" placeholder="手机/数码/关键字" />
			</view>
		</view>
		<view class="split"></view>
		<view>
			<title-bar icon="icon-shangpin" title="酷玩商品" Page="/pages/cool/cool"></title-bar>
		</view>

		<view class="cool-item-card-warp">
			<view class="cool-item-view" v-for="item in coolItems" :key="item.id" @tap="gotoDetail(item)">
				<cool-item-card @clickthumb="doThumb(item)" :id="item.id" :name="item.name" :title="item.title"
					:price="item.price" :thumb-count="item.thumbCount" :comment-count="item.commentCount"
					:image="buildImageUrl(item.image)" :is-thumb="item.isThumb"></cool-item-card>
			</view>
		</view>
		<view class="split"></view>
		<view>
			<title-bar icon="icon-baogao" title="报告精选" Page="/pages/report/report" :is-tab="false"></title-bar>
		</view>
		<view class="split"></view>



	</view>
</template>

<script>
	import TitleBar from '@/components/TitleBar.vue';
	import CoolItemCard from '@/components/CoolItemCard.vue';
	export default {
		data() {
			return {
				imageUrl: "",
				carousel: ["lunbo1.jpg", "lunbo2.jpg", "lunbo3.jpg", "lunbo4.jpg"], // 轮播图图片列表
				interval: 2000,
				coolItems: [],
				user: null
			}
		},
		components: {
			TitleBar,
			CoolItemCard
		},
		onShow() {
			this.user = getApp().globalData.user;
			uni.request({
				url: "/api/coolitem/search",
				data: {
					order: 'hot',
					pno: 1,
					size: 4
				},
				success: (resp) => {
					if (resp.data.code === 0 && resp.data.data) {
						this.coolItems = resp.data.data.list || [];
					}
				},
				fail: function(resp) {
					uni.showToast({
						title: "出错了"
					})
				}
			})
		},
		onLoad() {
			this.imageUrl = getApp().globalData.imageUrl;
		},
		methods: {
			// 构建完整的图片URL，添加时间戳防止缓存
			buildImageUrl(imagePath) {
				// 确保路径不为空
				if (!imagePath) return "";
				// 拼接完整的图片URL，并添加时间戳防止缓存
				const timestamp = new Date().getTime();
				return this.imageUrl + "/" + imagePath + "?t=" + timestamp;
			},
			gotoSearch() {
				uni.redirectTo({
					url: "/pages/search/search"
				})
			},
			gotoDetail(item) {
				uni.navigateTo({
					url: "/pages/cooldetail/cooldetail?id=" + item.id
				})
			},
			doThumb(data) {
				if (this.user == null) {
					uni.showToast({
						title: "请先登录！",
						icon: "error"
					});
					return;
				}
				let op = data.isThumb ? "cancel" : "add";
				uni.request({
					url: "/api/coolthumb/" + op,
					data: {
						itemId: data.id
					},
					success: (resp) => {
						if (resp.data.code) {
							uni.showToast({
								title: resp.data.message,
								icon: "error"
							});
							if (resp.data.code == 600) {
								if (op == "add") {
									data.isThumb = true;
								}
							}
						} else {
							if (op == "add") {
								data.isThumb = true;
								data.thumbCount++;
							} else {
								data.isThumb = false;
								data.thumbCount--;
							}
							uni.showToast({
								title: "操作成功",
								icon: "success"
							});
						}
					}

				})
			}
		}
	}
</script>

<style scoped>
	.split {
		height: 30rpx;
		background-color: lightblue;
	}

	.search-bar {
		width: 80%;
		height: 45rpx;
		margin: 20rpx 10%;
		position: fixed;
		top: 0;
		background-color: rgba(255, 255, 255, .6);
		border-radius: 20rpx;
		padding: 8rpx;
		box-sizing: border-box;
	}

	.search-bar view {
		float: left;
	}

	.search-bar .mysearch {
		font-size: 40rpx;
		color: blanchedalmond;
	}

	.search-bar input {
		padding-left: 20rpx;
	}

	.uni-margin-wrap .swiper {
		width: 750rpx;
		height: 375rpx;
	}

	.uni-margin-wrap .swiper image {
		width: 100%;
		height: 375rpx;
	}

	.myicon {
		font-size: 48rpx;
		color: red;
	}
</style>
